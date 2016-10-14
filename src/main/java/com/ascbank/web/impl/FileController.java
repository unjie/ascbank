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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.Test;
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

import com.ascbank.model.derive.FileInfo;
import com.ascbank.model.derive.FileType;
import com.ascbank.model.derive.HandleInfo;
import com.ascbank.util.FileUtilsIntensify;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
@Controller
@RequestMapping("/file")
public class FileController {
	
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

	private Path			driveRoot;

	private final Logger	log	= LoggerFactory.getLogger(FileController.class);
	
	// JSON Request content
	// {
	// "action": "compress",
	// "items": ["/public_html/photos", "/public_html/docs"],
	// "destination": "/public_html/backups",
	// "compressedFilename": "random-files.zip"
	// }}
	
	// JSON Response
	// { "result": { "success": true, "error": null } }
	@ResponseBody
	@RequestMapping(value = { "/compress" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo compress(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		log.debug("compress path: {} destination: {}", info.getPath(), info.getDestination());
		Path dirPath = driveRoot.resolve(Paths.get(info.getPath()));
		FileUtilsIntensify.ZipFile(dirPath.toFile(), Paths.get(dirPath.getParent() + ".zip").toFile());
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
	@ResponseBody
	@RequestMapping(value = { "/content" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo content(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		log.debug("---------{}-------------", jri);
		return jri;
	}
	
	// JSON Request content
	// {
	// "action": "createFolder",
	// "newPath": "/public_html/new-folder"
	// }
	
	// JSON Response
	// { "result": { "success": true, "error": null } }
	@ResponseBody
	@RequestMapping(value = "/create", method = { RequestMethod.PUT, RequestMethod.POST })
	public JsonResultInfo createDir(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		Path dirPath = driveRoot.resolve(Paths.get(info.getPath()));

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

	@RequestMapping(value = { "/downloads" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public void downloads(@RequestBody HandleInfo info) {
		
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
	@ResponseBody
	@RequestMapping(value = { "/eidt" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo eidt(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		log.debug("---------{}-------------", jri);
		return jri;
	}

	// JSON Request content
	//
	// {
	// "action": "extract",
	// "destination": "/public_html/extracted-files",
	// "item": "/public_html/compressed.zip"
	// }
	// JSON Response
	//
	// { "result": { "success": true, "error": null } }
	@ResponseBody
	@RequestMapping(value = { "/extract" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo extract(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		log.debug("---------{}-------------", jri);
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
	@ResponseBody
	@RequestMapping(value = { "/delete" }, method = { RequestMethod.DELETE })
	public JsonResultInfo fileDalete(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		Path pa = driveRoot.resolve(Paths.get(info.getPath()));
		pa.toFile().deleteOnExit();
		JsonResultInfo jri = new JsonResultInfo();
		jri.setMessage(info.getPath() + " Directory delete On Exit  Success!");
		jri.setSuccess(true);
		return jri;
	}
	
	// Http query params
	//
	// [fileManagerConfig.downloadFileUrl]?action=download&path=/public_html/image.jpg
	// Response
	//
	// -File content
	@RequestMapping(value = "/download", method = { RequestMethod.POST })
	public void fileDownload(@RequestBody HandleInfo info, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.debug("---------{}-------------", info);
		Path realPath = driveRoot.resolve(Paths.get(info.getPath()));

		// Catch download requests
		
		// [$config.downloadFileUrl]?mode=download&preview=true&path=/public_html/image.jpg
		String mode = request.getParameter("mode");
		boolean preview = BooleanUtils.toBoolean(request.getParameter("preview"));
		String path = request.getParameter("path");
		log.debug("doGet: {} mode: {} preview: {}", path, mode, preview);
		
		File file = realPath.toFile();// new File(REPOSITORY_BASE_PATH, path);
		
		if (!file.isFile()) {
			// if not a file, it is a folder, show this error.
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource Not Found");
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
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException logOrIgnore) {
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException logOrIgnore) {
				}
			}
		}
		
		// return "forward:" + realPath.toString();
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
	@ResponseBody
	@RequestMapping(value = "/reads", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResultInfo fileReads(@RequestBody HandleInfo info) throws IOException {
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
	@ResponseBody
	@RequestMapping(value = { "/upload" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo fileUpload(@RequestParam MultipartFile[] files, HttpServletRequest request) throws IOException {
		// 如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
		// 如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
		// 并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件

		JsonResultInfo jri = new JsonResultInfo();
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				jri.setSuccess(false);
				jri.setMessage("File undefined  ! ");
				log.info("file undefined !");
			} else {
				log.info("文件长度: {}", file.getSize());
				log.info("文件类型: {}", file.getContentType());
				log.info("文件名称: {}", file.getName());
				log.info("文件原名: {}", file.getOriginalFilename());
				// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
				// String realPath = request.getSession().getServletContext().getRealPath(systemConfig.getProperty("FILES_UPLOAD_PATH"));// "/WEB-INF/upload"
				// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(driveRoot.toString(), file.getOriginalFilename()));

			}
		}
		return jri;
	}
	
	/**
	 * @return the driveRoot
	 */
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
	@ResponseBody
	@RequestMapping(value = { "/move" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo move(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

		log.debug("---------{}-------------", jri);
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

	// JSON Response
	// { "result": { "success": true, "error": null } }
	@ResponseBody
	@RequestMapping(value = { "/permissions" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo permissions(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		
		log.debug("---------{}-------------", jri);
		return jri;
	}
	
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
	@ResponseBody
	@RequestMapping(value = { "/rename" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo rename(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();

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

	@Test
	public void test() throws IOException {
		HandleInfo hi = new HandleInfo();
		hi.setPath("/");
		FileController fc = new FileController();
		fc.setDriveRoot("X:/Workspaces/Java/GIT/ascbank/src/main/webapp/WEB-INF/upload");
		JsonResultInfo jri = fc.fileReads(hi);
		for (FileInfo fi : (List<FileInfo>) jri.getData()) {
			log.debug("-------------{}---------", fi);
		}

	}

}
