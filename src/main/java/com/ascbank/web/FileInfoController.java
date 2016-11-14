/**
 *
 */
package com.ascbank.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ascbank.model.derive.HandleInfo;
import com.ascbank.web.basis.JsonResultInfo;

/**
 * @author jie
 *
 */
public interface FileInfoController {

	JsonResultInfo compress(HandleInfo info);

	JsonResultInfo content(HandleInfo info);

	JsonResultInfo copy(HandleInfo info);

	JsonResultInfo createFolder(HandleInfo info);

	void download(String path, HttpServletResponse response);

	void downloads(HandleInfo info, HttpServletResponse response);

	JsonResultInfo eidt(HandleInfo info);

	JsonResultInfo extract(HandleInfo info);

	JsonResultInfo move(HandleInfo info);

	JsonResultInfo permissions(HandleInfo info);

	JsonResultInfo reads(HandleInfo info) throws IOException;

	JsonResultInfo remove(HandleInfo info);

	JsonResultInfo rename(HandleInfo info);

	JsonResultInfo upload(MultipartHttpServletRequest request, String destination);

}
