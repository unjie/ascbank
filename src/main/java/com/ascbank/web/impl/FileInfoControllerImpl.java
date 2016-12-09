/**
 *
 */
package com.ascbank.web.impl;

import java.io.File;
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

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.ascbank.util.FileEach;
import com.ascbank.util.FileUtilsIntensify;
import com.ascbank.web.FileInfoController;
import com.ascbank.web.basis.BaseAbstractController;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie <br/>
 * @serial listUrl: "file/reads",-- <br/>
 *         uploadUrl: "file/upload",-- <br/>
 *         renameUrl: "file/rename", <br/>
 *         copyUrl: "file/copy", <br/>
 *         moveUrl: "file/move", <br/>
 *         removeUrl: "file/remove", <br/>
 *         editUrl: "file/eidt", <br/>
 *         getContentUrl: "file/content", <br/>
 *         createFolderUrl: "file/create", <br/>
 *         downloadFileUrl: "file/download", <br/>
 *         downloadMultipleUrl: "file/downloads", <br/>
 *         compressUrl: "file/compress", <br/>
 *         extractUrl: "file/extract", <br/>
 *         permissionsUrl: "file/permissions",
 *
 * @info File Manage System
 */
@Controller
@RequestMapping("/file")
public class FileInfoControllerImpl extends BaseAbstractController<Long, FileInfo, FileInfoService<Long, FileInfo>> implements FileInfoController {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= -8861014305471354851L;
	
	private Path				driveRoot;
	
	private final Logger		log					= LoggerFactory.getLogger(FileInfoControllerImpl.class);
	
	/**
	 * 压缩文件 <br/>
	 * JSON Request content<br/>
	 * { "action": "compress", "items": ["/public_html/photos", "/public_html/docs"], "destination": "/public_html/backups", "compressedFilename": "random-files.zip" }} <br/>
	 * JSON Response<br/>
	 * { "result": { "success": true, "error": null } }
	 *
	 * @see com.ascbank.web.FileInfoController#compress(com.ascbank.model.derive.HandleInfo)
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/compress" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo compress(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		log.debug("compress Items: {} destination: {}", info.getItems(), info.getDestination());
		// Path dirPath = driveRoot.resolve(Paths.get(info.getPath()));
		
		try {
			FileUtilsIntensify.compress(driveRoot, info.getDestination(), info.getItems());
			jri.setSuccess(true);
			jri.setMessage("compress " + info.getItems() + "Success !");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("/compress Error {} ", e.getMessage());
			jri.setSuccess(false);
			jri.setMessage("compress " + info.getItems() + " Fail !");
		}
		log.debug("---------{}-------------", jri);
		return jri;
	}
	
	/**
	 * 读取内容 JSON Request content<br/>
	 * { "action": "getContent", "path": "/public_html/index.php" } <br/>
	 * JSON Response<br/>
	 * { "data": "<?php echo random(); ?>" }
	 *
	 * @see com.ascbank.web.FileInfoController#content(com.ascbank.model.derive.HandleInfo)
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/content" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo content(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		/*
		 * Path dirPath = driveRoot.resolve(Paths.get(info.getPath())); File file = dirPath.toFile(); if (!file.isFile()) { // if not a file, it is a folder, show this error. jri.setError(info.getPath() + " not File ! "); jri.setSuccess(false); return jri; }
		 */
		try {
			jri.setData(FileUtilsIntensify.readFileToString(driveRoot.resolve(info.getPath()).toFile()));
			jri.setMessage(info.getPath() + " File Read Success ! ");
			jri.setSuccess(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("{} ERROR {}", info.getPath(), e);
			jri.setError(" ERROR : " + e);
		}
		log.debug("---------{}-------------", jri);
		return jri;
	}
	
	/**
	 * 复制<br/>
	 * JSON Request content<br/>
	 * {Items:{'file1.js',file2.js},Destination:'./new/'}
	 *
	 * @see com.ascbank.web.FileInfoController#copy(com.ascbank.model.derive.HandleInfo)
	 **/
	@Override
	@ResponseBody
	@RequestMapping(value = { "/copy" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo copy(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		try {
			FileUtilsIntensify.copy(driveRoot, info.getDestination(), info.getContent(), info.getItems());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			jri.setSuccess(false);
			jri.setMessage("/copy File Fail ! ");
			jri.setError(jri.getError() + " \n " + e.getMessage());
			e.printStackTrace();
		}
		jri.setSuccess(true);
		jri.setMessage("/copy File Success ! ");
		return jri;
		
	}
	
	/**
	 * 创建文件夹<br/>
	 * JSON Response<br>
	 * { "success": true, "error": null }<br/>
	 * JSON Request content <br/>
	 * "action": "createFolder", <br/>
	 * "Destination": "/public_html/new-folder" <br/>
	 * }
	 *
	 * @see com.ascbank.web.FileInfoController#createFolder(com.ascbank.model.derive.HandleInfo)
	 **/
	@Override
	@ResponseBody
	@RequestMapping(value = "/create", method = { RequestMethod.PUT, RequestMethod.POST })
	public JsonResultInfo createFolder(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		if (FileUtilsIntensify.createFolder(driveRoot, info.getDestination())) {
			jri.setMessage("Directory mkdirs successs !");
			jri.setSuccess(true);
		} else {
			jri.setMessage("Directory mkdirs  Failure!");
			jri.setSuccess(false);
		}
		
		return jri;
	}
	
	/**
	 * 单个文件下载<br/>
	 * Http query params<br>
	 * [fileManagerConfig.downloadFileUrl]?action=download&path=/public_html/image.jpg <br/>
	 * Response <br/>
	 * -File content
	 *
	 * @see com.ascbank.web.FileInfoController#download(java.lang.String, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@RequestMapping(value = { "/download" }, method = { RequestMethod.POST, RequestMethod.GET })
	public void download(@PathVariable("path") @RequestParam("path") String path, HttpServletResponse response) {
		log.debug("---------{}-------------", path);
		File file = FileUtilsIntensify.getFile(driveRoot, path);
		if (!file.isFile()) {
			// if not a file, it is a folder, show this error.
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource Not Found");
				response.setStatus(404);
				log.error("/download  Resource Not Found {}  ", path);
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
		
		try {
			FileUtilsIntensify.copyFile(file, response.getOutputStream());
		} catch (IOException t) {
			log.error(">>>>      /download IOExcption {}  ", t);
			
		}
		
		// return "forward:" + realPath.toString();
	}
	
	/**
	 * 多个文件打包压缩下载 <br/>
	 * JSON Request content <br/>
	 * { "items": ["/public_html/image1.jpg", "/public_html/image2.jpg"], "Content(": "multiple-items.zip" }} <br/>
	 * Response <br/>
	 * -File content
	 *
	 * @see com.ascbank.web.FileInfoController#downloads(com.ascbank.model.derive.HandleInfo, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@RequestMapping(value = { "/downloads" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public void downloads(@RequestBody HandleInfo info, HttpServletResponse response) {
		
		try {
			Path zippath = FileUtilsIntensify.zipFiles(driveRoot, info.getContent(), info.getItems());
			
			this.download(zippath.toString(), response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			log.error("/downloads Error {} ", e.getMessage());
		}
	}
	
	/**
	 * 编辑文件内容 <br/>
	 * JSON Request content <br/>
	 * { "action": "edit", "item": "/public_html/index.php", "content": "<?php echo random(); ?>" } <br/>
	 * JSON Response <br/>
	 * { "result": { "success": true, "error": null } }
	 *
	 * @see com.ascbank.web.FileInfoController#eidt(com.ascbank.model.derive.HandleInfo)
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/eidt" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo eidt(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		
		try {
			FileUtilsIntensify.writeStringToFile(driveRoot.resolve(info.getPath()), info.getContent());
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
	
	/**
	 * 解压指定文件<br/>
	 * JSON Request content <br/>
	 * { <br/>
	 * "action": "extract", <br/>
	 * "destination": "/public_html/extracted-files", <br/>
	 * "path": "/public_html/compressed.zip" <br/>
	 * } <br/>
	 * JSON Response <br/>
	 * { "result": { "success": true, "error": null } }
	 *
	 * @see com.ascbank.web.FileInfoController#extract(com.ascbank.model.derive.HandleInfo)
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/extract" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo extract(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		try {
			FileUtilsIntensify.uncompress(driveRoot, info.getDestination(), info.getPath());
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
	
	/**
	 * 移动指定文件 <br/>
	 * JSON Request content <br/>
	 * { <br/>
	 * "action": "move", <br/>
	 * "items": ["/public_html/libs", "/public_html/config.php"], <br/>
	 * "Destination": "/public_html/includes" <br/>
	 * } <br/>
	 * JSON Response <br/>
	 * { "result": { "success": true, "error": null } }
	 *
	 * @see com.ascbank.web.FileInfoController#move(com.ascbank.model.derive.HandleInfo)
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/move" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo move(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		try {
			FileUtilsIntensify.move(driveRoot, info.getDestination(), info.getItems());
			jri.setSuccess(true);
			jri.setMessage("/move File Success ! ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			jri.setSuccess(false);
			jri.setMessage("/move File Fail ! ");
			jri.setError(jri.getError() + " \n " + e.getMessage());
			e.printStackTrace();
		}
		
		/////////////////////////////////////////////////////////////
		
		log.debug("---------{}-------------", jri);
		return jri;
	}
	
	/**
	 * 设置权限 (未完成) <br/>
	 * JSON Request content <br/>
	 * { <br/>
	 * "action": "changePermissions", <br/>
	 * "items": ["/public_html/root", "/public_html/index.php"], <br/>
	 * "perms": "653", <br/>
	 * "permsCode": "rw-r-x-wx", <br/>
	 * "recursive": true <br/>
	 * }
	 *
	 * @see com.ascbank.web.FileInfoController#permissions(com.ascbank.model.derive.HandleInfo)
	 */
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
	
	/**
	 * 读取文明目录下文件<br/>
	 * JSON Request content <br/>
	 * { "action": "list", "path": "/public_html" } <br/>
	 * JSON Response <br/>
	 * { "data": [ <br/>
	 * { "name": "magento", "rights": "drwxr-xr-x", "size": "4096", "date": "2016-03-03 15:31:40", "type": "dir" },<br/>
	 * { "name": "index.php", "rights": "-rw-r--r--", "size": "549923", "date": "2016-03-03 15:31:40", "type": "file" } <br/>
	 * ]}
	 *
	 * @see com.ascbank.web.FileInfoController#reads(com.ascbank.model.derive.HandleInfo)
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = "/reads", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResultInfo reads(@RequestBody HandleInfo info) {
		JsonResultInfo jri = new JsonResultInfo();
		Path infoPath = driveRoot.resolve(info.getPath());
		List<FileInfo> data = new ArrayList<FileInfo>();
		try {
			FileUtilsIntensify.directoryEach(infoPath, new FileEach() {
				@Override
				public void each(File file) throws IOException {
					// TODO Auto-generated method stub
					log.debug("---each  -{} ", file.getName());
					FileInfo varFileInfo = new FileInfo();
					
					BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
					log.debug(">>>>>>>>{}", file.getPath());
					varFileInfo.setName(file.getName());
					varFileInfo.setPath(driveRoot.relativize(file.toPath()).toString().replaceAll("\\\\", "/"));
					varFileInfo.setDate(new Date(attrs.creationTime().toMillis()));
					varFileInfo.setType(file.isDirectory() ? FileType.dir : FileType.file);
					varFileInfo.setSize(attrs.size());
					varFileInfo.setPerms(null);
					
					data.add(varFileInfo);
				}
			});
			log.debug("--{}----------", data);
			jri.setData(data);
			jri.setSuccess(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jri.setSuccess(false);
			jri.setError(e.getMessage());
		}
		
		log.debug("--{}----------", jri);
		return jri;
	}
	
	/**
	 * 移除文件 <br/>
	 * JSON Request content <br/>
	 * { "action": "remove", "items": ["/public_html/index.php"], } <br/>
	 * JSON Response <br/>
	 * { "result": { "success": true, "error": null } }
	 *
	 * @see com.ascbank.web.FileInfoController#remove(com.ascbank.model.derive.HandleInfo)
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/remove" }, method = { RequestMethod.DELETE, RequestMethod.POST })
	public JsonResultInfo remove(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		int delFileNum = FileUtilsIntensify.remove(driveRoot, info.getItems()),
				fnum = info.getItems().length;
		if (delFileNum == fnum) {
			jri.setSuccess(true);
			jri.setMessage("Delete File " + delFileNum + " Success!");
		} else {
			jri.setSuccess(false);
			jri.setMessage("Delete File " + (fnum - delFileNum) + " Fail !");
		}
		
		return jri;
	}
	
	/**
	 * 重命名 <br/>
	 * JSON Request content <br/>
	 * { <br/>
	 * "action": "rename", <br/>
	 * "item": "/public_html/index.php", <br/>
	 * "newItemPath": "/public_html/index2.php" <br/>
	 * } <br/>
	 * JSON Response <br/>
	 * <br/>
	 * { "result": { "success": true, "error": null } }
	 *
	 * @see com.ascbank.web.FileInfoController#rename(com.ascbank.model.derive.HandleInfo)
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/rename" }, method = { RequestMethod.OPTIONS, RequestMethod.POST })
	public JsonResultInfo rename(@RequestBody HandleInfo info) {
		log.debug("---------{}-------------", info);
		JsonResultInfo jri = new JsonResultInfo();
		
		log.debug("-------{}---------", info.getPath());
		try {
			FileUtilsIntensify.move(driveRoot, info.getDestination(), info.getPath());
			jri.setSuccess(true);
			jri.setMessage("rename  " + info.getPath() + " to " + info.getDestination() + "Success !");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jri.setSuccess(true);
			jri.setMessage("rename  " + info.getPath() + " to " + info.getDestination() + " Fail !");
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
	
	/**
	 * 上传文件 <br/>
	 * Http post request payload <br/>
	 * ------WebKitFormBoundaryqBnbHc6RKfXVAf9j <br/>
	 * Content-Disposition: form-data; name="destination" <br/>
	 * / <br/>
	 * <br/>
	 * ------WebKitFormBoundaryqBnbHc6RKfXVAf9j <br/>
	 * Content-Disposition: form-data; name="file[0]"; filename="github.txt" <br/>
	 * Content-Type: text/plain <br/>
	 * JSON Response <br/>
	 * { "result": { "success": true, "error": null } }
	 *
	 * @see com.ascbank.web.FileInfoController#upload(org.springframework.web.multipart.MultipartHttpServletRequest, java.lang.String)
	 */
	@Override
	@ResponseBody
	@RequestMapping(value = { "/upload" }, method = { RequestMethod.POST })
	public JsonResultInfo upload(MultipartHttpServletRequest request, @RequestParam(name = "destination") String destination) {
		// 如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
		// 如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
		// 并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件
		log.debug("----/upload Dir {} ", destination);
		JsonResultInfo jri = new JsonResultInfo();
		Map<String, MultipartFile> files = request.getMultiFileMap().toSingleValueMap();
		log.debug("----/upload files length {} ", files.size());
		jri.setSuccess(true);
		FileInfo fileInfos[] = new FileInfo[files.size()];
		int i = 0;
		
		for (MultipartFile file : files.values()) {
			if (file.isEmpty()) {
				jri.setMessage("File undefined  ! ");
				log.debug("file undefined !");
			} else {
				log.debug("文件长度: {}", file.getSize());
				log.debug("文件类型: {}", file.getContentType());
				log.debug("文件名称: {}", file.getName());
				log.debug("文件原名: {}", file.getOriginalFilename());
				Path path = driveRoot.resolve(destination).resolve(file.getOriginalFilename());
				File pathFile = path.toFile();
				// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
				// String realPath = request.getSession().getServletContext().getRealPath(systemConfig.getProperty("FILES_UPLOAD_PATH"));// "/WEB-INF/upload"
				// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
				try {
					if (!pathFile.exists()) {
						pathFile.mkdirs();
					}
					file.transferTo(pathFile);// copyInputStreamToFile(file.getInputStream(), driveRoot.resolve(path.toFile());
					fileInfos[i] = new FileInfo();
					// FileInfo finfo = fileInfos[i].clone();
					fileInfos[i].setPath(driveRoot.relativize(path).toString().replaceAll("\\\\", "/"));
					i++;
					jri.setSuccess(jri.isSuccess() && true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error("/upload  {} Fail !", file.getName());
					jri.setError(jri.getError() + " /upload " + path + " Fail !");
					e.printStackTrace();
				}
			}
		}
		jri.setData(fileInfos);
		return jri;
	}
	
}
