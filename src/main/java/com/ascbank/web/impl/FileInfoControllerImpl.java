/**
 *
 */
package com.ascbank.web.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ascbank.model.FileInfo;
import com.ascbank.model.derive.FileType;
import com.ascbank.model.derive.HandleInfo;
import com.ascbank.service.FileInfoService;
import com.ascbank.util.FileUtilsIntensify;
import com.ascbank.web.FileInfoController;
import com.ascbank.web.basis.BaseAbstractController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/file")
public class FileInfoControllerImpl extends BaseAbstractController<Long, FileInfo, FileInfoService<Long, FileInfo>> implements FileInfoController {

	// listUrl: "file/reads",--
	// uploadUrl: "file/upload",--
	// renameUrl: "file/rename",
	// copyUrl: "file/copy",
	// moveUrl: "file/move",
	// removeUrl: "file/remove",
	// editUrl: "file/eidt",
	// getContentUrl: "file/content",
	// createFolderUrl: "file/create",
	// downloadFileUrl: "file/download",
	// downloadMultipleUrl: "file/downloads",
	// compressUrl: "file/compress",
	// extractUrl: "file/extract",
	// permissionsUrl: "file/permissions",

	/**
	 *
	 */
	private static final long	serialVersionUID	= -8861014305471354851L;

	private Path				driveRoot;

	private final Logger		log					= LoggerFactory.getLogger(FileInfoControllerImpl.class);

	// JSON Request content
	// {
	// "action": "compress",
	// "items": ["/public_html/photos", "/public_html/docs"],
	// "destination": "/public_html/backups",
	// "compressedFilename": "random-files.zip"
	// }}

	// JSON Response
	// { "result": { "success": true, "error": null } }
	@Override
	@ResponseBody
	@RequestMapping(value = { "/compress" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo compress(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		log.debug("compress Items: {} destination: {}", info.getItems(), info.getDestination());
		// Path dirPath = driveRoot.resolve(Paths.get(info.getPath()));
		ZipOutputStream zipOut = null;
		jri.setSuccess(true);
		jri.setMessage("compress " + info.getItems() + "Success !");
		try {
			zipOut = FileUtilsIntensify.getZipOutputStream(driveRoot.resolve(Paths.get(info.getDestination())).toFile());
			for (String p : info.getItems()) {
				File f = driveRoot.resolve(Paths.get(p)).toFile();
				FileUtilsIntensify.putFileZip(zipOut, f);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("/compress Error {} ", e.getMessage());
			log.debug("/compress Error {} ", e.getMessage());

			jri.setSuccess(false);
			jri.setMessage("compress " + info.getItems() + " Fail !");
		} finally {
			if (zipOut != null) {
				try {
					zipOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("/compress Error finally {} ", e.getMessage());
				}
			}
		}
		log.debug("---------{}-------------", jri);
		return jri;
	}

	// JSON Request content
	// {
	// "action": "getContent",
	// "item": "/public_html/index.php"
	// }

	// JSON Response
	// { "result": "<?php echo random(); ?>" }
	@Override
	@ResponseBody
	@RequestMapping(value = { "/content" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo content(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		Path dirPath = driveRoot.resolve(Paths.get(info.getPath()));
		File file = dirPath.toFile();

		if (!file.isFile()) {
			// if not a file, it is a folder, show this error.
			jri.setError(info.getPath() + " not File ! ");
			jri.setSuccess(false);
			return jri;
		}

		try {
			jri.setData(FileUtils.readFileToString(file));
			jri.setMessage(info.getPath() + " File Read Success ! ");
			jri.setSuccess(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("{} ERROR {}", dirPath, e);
			jri.setError(" ERROR : " + e);
		}

		log.debug("---------{}-------------", jri);
		return jri;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = { "/copy" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo copy(@RequestBody HandleInfo info) {

		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		Path dirPath = driveRoot.resolve(Paths.get(info.getNewPath()));

		File srcFile = null, destFile = null;
		jri.setSuccess(true);
		jri.setMessage("/copy File Success ! ");
		for (String p : info.getItems()) {

			srcFile = driveRoot.resolve(Paths.get(p)).toFile();
			destFile = dirPath.resolve(Paths.get(((info.getItems().length > 1) ? srcFile.getName() : info.getToFilename()))).toFile();
			try {
				if (srcFile.isFile()) {
					FileUtils.copyFile(srcFile, destFile);
				} else {
					FileUtils.copyDirectory(srcFile, destFile);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				jri.setSuccess(false);
				jri.setMessage("/copy File Fail ! ");
				jri.setError(jri.getError() + " \n " + e.getMessage());
				e.printStackTrace();
			}
		}

		return jri;
	}

	// JSON Response
	// { "result": { "success": true, "error": null } }

	// JSON Request content
	// {
	// "action": "createFolder",
	// "newPath": "/public_html/new-folder"
	// }
	@Override
	@ResponseBody
	@RequestMapping(value = "/create", method = { RequestMethod.PUT, RequestMethod.POST })
	public JsonResultInfo createFolder(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		Path dirPath = driveRoot.resolve(Paths.get(info.getNewPath()));

		if (dirPath.toFile().exists()) {
			jri.setMessage("Directory Exist !");
			jri.setSuccess(true);
		} else {

			if (dirPath.toFile().mkdirs()) {
				jri.setMessage("Directory mkdirs successs !");
				jri.setSuccess(true);
			} else {
				jri.setMessage("Directory mkdirs  Failure!");
				jri.setSuccess(false);
			}

		}
		return jri;
	}

	// Http query params
	//
	// [fileManagerConfig.downloadFileUrl]?action=download&path=/public_html/image.jpg
	// Response
	//
	// -File content
	@Override
	@RequestMapping(value = "/download", method = { RequestMethod.POST, RequestMethod.GET })
	public void download(@RequestParam("path") String path, HttpServletResponse response) {
		log.debug("---------{}-------------", path);
		Path realPath = driveRoot.resolve(Paths.get(path));

		// Catch download requests

		// [$config.downloadFileUrl]?mode=download&preview=true&path=/public_html/image.jpg
		// String mode = request.getParameter("mode");
		// boolean preview = BooleanUtils.toBoolean(request.getParameter("preview"));
		// String path = request.getParameter("path");
		log.debug("/download: {}  ", realPath);

		File file = realPath.toFile();// new File(REPOSITORY_BASE_PATH, path);

		if (!file.isFile()) {
			// if not a file, it is a folder, show this error.
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource Not Found");
				log.error("/download  Resource Not Found {}  ", realPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("/download IOExcption {}  ", e);
				e.printStackTrace();
			}
			return;
		}

		// se imageName non ha estensione o non è immagine sballa! ;)
		// response.setHeader("Content-Type", getServletContext().getMimeType(imageName));

		response.setHeader("Content-Type", new MimetypesFileTypeMap().getContentType(file));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

		FileInputStream input = null;
		BufferedOutputStream output = null;
		try {

			input = new FileInputStream(file);
			output = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[8192];
			for (int length = 0; (length = input.read(buffer)) > 0;) {
				output.write(buffer, 0, length);
			}
		} catch (Throwable t) {

			log.error(">>>>      /download IOExcption {}  ", t);

		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException logOrIgnore) {
					log.error(">>>>      /download  output    logOrIgnore {}  ", logOrIgnore);
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException logOrIgnore) {
					log.error(">>>>      /download  input    logOrIgnore {}  ", logOrIgnore);
				}
			}
		}

		// return "forward:" + realPath.toString();
	}

	// JSON Request content
	//
	// {
	// "action": "downloadMultiple",
	// "items": ["/public_html/image1.jpg", "/public_html/image2.jpg"],
	// "toFilename": "multiple-items.zip"
	// }}
	// Response
	//
	// -File content
	@Override
	@RequestMapping(value = { "/downloads" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public void downloads(@RequestBody HandleInfo info, HttpServletResponse response) {
		ZipOutputStream zipOut = null;
		Path zippath = Paths.get(info.getToFilename());
		Path path = driveRoot.resolve(zippath);
		try {
			zipOut = FileUtilsIntensify.getZipOutputStream(path.toFile());
			for (String p : info.getItems()) {
				File f = driveRoot.resolve(Paths.get(p)).toFile();
				FileUtilsIntensify.putFileZip(zipOut, f);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("/downloads Error {} ", e.getMessage());
		} finally {
			if (zipOut != null) {
				try {
					zipOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("/downloads Error finally {} ", e.getMessage());
				}
			}
		}
		info.setPath(zippath.toString());
		this.download(zippath.toString(), response);
	}

	// JSON Request content
	//
	// {
	// "action": "edit",
	// "item": "/public_html/index.php",
	// "content": "<?php echo random(); ?>"
	// }
	// JSON Response
	//
	// { "result": { "success": true, "error": null } }
	@Override
	@ResponseBody
	@RequestMapping(value = { "/eidt" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo eidt(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		Path pa = driveRoot.resolve(Paths.get(info.getPath()));
		try {
			FileUtils.writeStringToFile(pa.toFile(), info.getContent());
			jri.setSuccess(true);
			jri.setMessage(info.getPath() + " File Save Success !");
		} catch (Exception e) {
			log.error("/edit saveFile  {}", e);
			jri.setSuccess(false);
			jri.setMessage(info.getPath() + " File Save  Fail!");

		}

		log.debug("---------{}-------------", jri);
		return jri;
	}

	// JSON Request content
	//
	// {
	// "action": "extract",
	// "destination": "/public_html/extracted-files",
	// "path": "/public_html/compressed.zip"
	// }
	// JSON Response
	//
	// { "result": { "success": true, "error": null } }
	@Override
	@ResponseBody
	@RequestMapping(value = { "/extract" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo extract(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		Path pa = driveRoot.resolve(Paths.get(info.getPath()));

		try {
			FileUtilsIntensify.uncompress(pa.toFile(), driveRoot.resolve(Paths.get(info.getDestination())));

			jri.setSuccess(true);
			jri.setMessage("File uncompress Success !");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jri.setSuccess(false);
			jri.setMessage("File uncompress Fail !");
		}
		log.debug("---------{}-------------", jri);
		return jri;
	}

	/**
	 * @return the driveRoot
	 */
	// @Override
	public Path getDriveRoot() {
		return driveRoot;
	}

	// JSON Request content
	//
	// {
	// "action": "move",
	// "items": ["/public_html/libs", "/public_html/config.php"],
	// "newPath": "/public_html/includes"
	// }
	// JSON Response
	//
	// { "result": { "success": true, "error": null } }
	@Override
	@ResponseBody
	@RequestMapping(value = { "/move" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo move(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		//////////////////////////////////////////////////////////////

		File destFile = null;
		File srcFile = null;

		jri.setSuccess(true);
		jri.setMessage("/move File Success ! ");
		for (String p : info.getItems()) {
			try {
				srcFile = driveRoot.resolve(Paths.get(p)).toFile();
				destFile = driveRoot.resolve(Paths.get(info.getNewPath(), srcFile.getName())).toFile();
				if (srcFile.isFile()) {
					FileUtils.moveFile(srcFile, destFile);
				} else {
					FileUtils.moveDirectory(srcFile, destFile);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				jri.setSuccess(false);
				jri.setMessage("/move File Fail ! ");
				jri.setError(jri.getError() + " \n " + e.getMessage());
				e.printStackTrace();
			}

		}

		/////////////////////////////////////////////////////////////

		log.debug("---------{}-------------", jri);
		return jri;
	}

	// JSON Response
	// { "result": { "success": true, "error": null } }
	@Override
	@ResponseBody
	@RequestMapping(value = { "/permissions" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo permissions(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		File file = driveRoot.resolve(Paths.get(info.getPath())).toFile();

		try {
			setPermissions(file, info.getPermsCode(), info.getRecursive());

			jri.setSuccess(true);
			jri.setMessage("File permissions " + info.getPermsCode() + " Success !");
		} catch (IOException e) {
			// TODO Auto-generated catch block

			jri.setSuccess(false);
			jri.setMessage("File permissions " + info.getPermsCode() + " File !");
			e.printStackTrace();
		}

		log.debug("---------{}-------------", jri);
		return jri;
	}

	// JSON Request content
	//
	// {
	// "action": "list",
	// "path": "/public_html"
	// }
	// JSON Response
	//
	// { "result": [
	// {
	// "name": "magento",
	// "rights": "drwxr-xr-x",
	// "size": "4096",
	// "date": "2016-03-03 15:31:40",
	// "type": "dir"
	// }, {
	// "name": "index.php",
	// "rights": "-rw-r--r--",
	// "size": "549923",
	// "date": "2016-03-03 15:31:40",
	// "type": "file"
	// }
	// ]}
	@Override
	@ResponseBody
	@RequestMapping(value = "/reads", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResultInfo reads(@RequestBody HandleInfo info) throws IOException {
		log.debug("---------{}-----{}--------", info, driveRoot.toString());

		JsonResultInfo jri = new JsonResultInfo();
		List<FileInfo> data = new ArrayList<FileInfo>();
		log.debug("-------{}---------", driveRoot);

		log.debug("-------{}---------", info.getPath());
		Path infoPath = driveRoot.resolve(Paths.get(info.getPath()));

		log.debug("--{}----------", infoPath);
		File infoFile = infoPath.toFile();
		log.debug("--{}----------", infoFile.getPath());
		log.debug("-------9090      {}-------", driveRoot.toUri());
		if (infoFile.isDirectory()) {
			log.debug("--{}----------", infoFile);
			FileInfo varFileInfo = new FileInfo();
			for (File file : infoFile.listFiles()) {
				FileInfo finfo = varFileInfo.clone();
				BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				log.debug(">>>>>>>>{}", file.getPath());
				finfo.setName(file.getName());
				finfo.setPath(driveRoot.relativize(file.toPath()).toString().replaceAll("\\\\", "/"));
				finfo.setDate(new Date(attrs.creationTime().toMillis()));
				finfo.setType(file.isDirectory() ? FileType.dir : FileType.file);
				finfo.setSize(attrs.size());
				finfo.setPerms(null);

				data.add(finfo);
			}

		}
		log.debug("--{}----------", data);
		jri.setData(data);
		log.debug("--{}----------", jri);
		return jri;
	}

	// JSON Request content
	//
	// {
	// "action": "remove",
	// "items": ["/public_html/index.php"],
	// }
	// JSON Response
	//
	// { "result": { "success": true, "error": null } }
	@Override
	@ResponseBody
	@RequestMapping(value = { "/remove" }, method = { RequestMethod.DELETE, RequestMethod.POST })
	public JsonResultInfo remove(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		File file = null;
		for (String p : info.getItems()) {
			file = driveRoot.resolve(Paths.get(p)).toFile();
			log.debug("---------{}-------------", p);
			if (file.exists()) {
				if (FileUtils.deleteQuietly(file)) {
					jri.setSuccess(true);
				} else {
					jri.setError(jri.getError() + p + " Delete  Fail ! \n");
				}

				jri.setMessage(info.getPath() + " Directory delete On Exit  Success!");
			} else {
				jri.setMessage(info.getPath() + " Directory delete On Exit  Fail!");
				jri.setSuccess(false);
			}
		}

		return jri;
	}

	// JSON Request content
	// {
	// "action": "changePermissions",
	// "items": ["/public_html/root", "/public_html/index.php"],
	// "perms": "653",
	// "permsCode": "rw-r-x-wx",
	// "recursive": true
	// }

	// JSON Request content
	//
	// {
	// "action": "rename",
	// "item": "/public_html/index.php",
	// "newItemPath": "/public_html/index2.php"
	// }
	// JSON Response
	//
	// { "result": { "success": true, "error": null } }
	@Override
	@ResponseBody
	@RequestMapping(value = { "/rename" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo rename(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		log.debug("-------{}---------", info.getPath());
		try {
			File srcFile = driveRoot.resolve(Paths.get(info.getPath())).toFile();
			File destFile = driveRoot.resolve(Paths.get(info.getNewPath())).toFile();
			if (srcFile.isFile()) {
				FileUtils.moveFile(srcFile, destFile);
			} else {
				FileUtils.moveDirectory(srcFile, destFile);
			}

			jri.setSuccess(true);
			jri.setMessage("rename  " + info.getPath() + " to " + info.getNewPath() + "Success !");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jri.setSuccess(true);
			jri.setMessage("rename  " + info.getPath() + " to " + info.getNewPath() + " Fail !");
		}
		log.debug("---------{}-------------", jri);
		return jri;
	}

	/**
	 * @param driveRoot
	 *            the driveRoot to set
	 */
	// @Resource(name = "fileUploadPath")
	@Value("#{systemConfig.WEB_ROOT_PATH}#{systemConfig.FILES_UPLOAD_PATH}")
	public void setDriveRoot(String driveRoot) {
		log.debug("---------{}--", driveRoot);
		this.driveRoot = Paths.get(driveRoot);
	}

	private String setPermissions(File file, String permsCode, boolean recursive) throws IOException {
		// http://www.programcreek.com/java-api-examples/index.php?api=java.nio.file.attribute.PosixFileAttributes
		PosixFileAttributeView fileAttributeView = Files.getFileAttributeView(file.toPath(), PosixFileAttributeView.class);
		fileAttributeView.setPermissions(PosixFilePermissions.fromString(permsCode));
		if (file.isDirectory() && recursive && file.listFiles() != null) {
			for (File f : file.listFiles()) {
				setPermissions(f, permsCode, recursive);
			}
		}
		return permsCode;
	}

	/*
	 * @Test public void test() throws IOException { HandleInfo hi = new HandleInfo(); hi.setPath("/"); FileController fc = new FileController(); fc.setDriveRoot("X:/Workspaces/Java/GIT/ascbank/src/main/webapp/WEB-INF/upload"); JsonResultInfo jri = fc.reads(hi); for (FileInfo fi : (List<FileInfo>) jri.getData()) { log.debug("-------------{}---------", fi); }
	 *
	 * }
	 */

	// Http post request payload
	//
	// ------WebKitFormBoundaryqBnbHc6RKfXVAf9j
	// Content-Disposition: form-data; name="destination"
	// /
	//
	// ------WebKitFormBoundaryqBnbHc6RKfXVAf9j
	// Content-Disposition: form-data; name="file-0"; filename="github.txt"
	// Content-Type: text/plain
	// JSON Response
	//
	// { "result": { "success": true, "error": null } }
	@Override
	@ResponseBody
	@RequestMapping(value = { "/upload" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo upload(MultipartHttpServletRequest request, @RequestParam(name = "destination") String destination) {
		// 如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
		// 如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
		// 并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件
		log.debug("----/upload Dir {} ", destination);
		JsonResultInfo jri = new JsonResultInfo();
		Map<String, MultipartFile> files = request.getMultiFileMap().toSingleValueMap();
		log.debug("----/upload files length {} ", files.size());

		for (MultipartFile file : files.values()) {

			if (file.isEmpty()) {
				jri.setSuccess(false);
				jri.setMessage("File undefined  ! ");
				log.debug("file undefined !");
			} else {
				log.debug("文件长度: {}", file.getSize());
				log.debug("文件类型: {}", file.getContentType());
				log.debug("文件名称: {}", file.getName());
				log.debug("文件原名: {}", file.getOriginalFilename());
				// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
				// String realPath = request.getSession().getServletContext().getRealPath(systemConfig.getProperty("FILES_UPLOAD_PATH"));// "/WEB-INF/upload"
				// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(), driveRoot.resolve(Paths.get(destination, file.getOriginalFilename())).toFile());

				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error("/upload  {} Fail !", file.getName());

					jri.setError(jri.getError() + " /upload " + file.getName() + " Fail !");
					e.printStackTrace();
				}

			}
		}
		return jri;
	}

}
