<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>附件上传页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/bootstrap-3.2.0-dist/css/bootstrap.css">
<link rel="stylesheet" href="<%=path%>/parts/CSS/upload/css/style.css">
<link rel="stylesheet" href="<%=path%>/parts/CSS/upload/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="<%=path%>/parts/CSS/upload/css/bootstrap-image-gallery.min.css">
<link rel="stylesheet" href="<%=path%>/parts/CSS/upload/css/jquery.fileupload-ui.css">
<style type="text/css">
pre{padding-top:20px;}
</style>
<script charset="utf-8" src="<%=path%>/parts/JS/jquery-1.4.4.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/customDialog.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/jquery.artDialog.js?skin=black"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.iframeTools.js"></script>
<script type="text/javascript">
//获得服务器上已经上传的文件列表
  
   function colsefunc(){
   		art.dialog.close();
   }
  
</script>
</head>
<body>
<form enctype="multipart/form-data" action="<%=path%>/archiveattach/uploadfile.html" method="POST" id="fileupload" >
<div class="container">
		
		<input type="hidden" id="ctx" value="<%=path%>" />
		<input type="hidden" id="archive_type" name="archive_type" value="${param.archive_type}"/>
		<input type="hidden" id="archive_id" name="archive_id" value="${param.archive_id}"/>
		<input type="hidden" id="size" name="size" value="10000"/>
		<input type="hidden" id="browser" name="browser" value="${param.browser}"/>
        
        <c:choose>
            	<c:when test="${'txxx' eq param.type || 'sfxx' eq param.type}">
            		<input type="hidden" id="type" name="type" value="jpg|bmp|png|jpeg|gif|tif|tiff"/>
                </c:when>
            	<c:otherwise>
                    <input type="hidden" id="type" name="type" value="mdb|doc|docx|xls|xlsx|ppt|pptx|pdf|jpg|bmp|png|jpeg|gif|tif|tiff|rar|zip|7z|mov|rm|rmvb|avi|flv|mp4|3gp|wmv|mpeg|mpg|wps|et|xlsx"/>    
                </c:otherwise>
 		</c:choose>
        
        <div class="row fileupload-buttonbar">
            <div class="span7">
                <span class="btn btn-success fileinput-button">
                    <i class="icon-plus icon-white"></i>
                    <span>添加文件</span>
                    <input type="file" name="files[]" multiple>
                </span>
                <button type="submit" class="btn btn-primary start">
                    <i class="icon-upload icon-white"></i>
                    <span >开始上传</span>
                </button>
                <button type="reset" class="btn btn-warning cancel"  onclick="return colsefunc();">
                    <i class="icon-ban-circle icon-white"></i>
                    <span>关闭窗口</span>
                </button>
            </div>
<h3>一、拍摄主题及范围</h3>

	<p style="margin-left:30px;">以黑龙江为主题，围绕龙江人文、风光为依托，尽情展示龙江生态之大美。</p>

			<h3>二、作品规格及要求</h3>
			<ul style="list-style:none;">
				<li>1.作品必须在黑龙江省境内拍摄，要求真实，形象地表现主题，不能改变现实原貌。不接收电脑特技制作照片。</li>
			    <li>2.参赛作品电子版规格不小于3MB，篇幅不限。本次大赛，仅限单幅作品奖，本次活动不设组图奖。</li>
				<li>3.作品题材、形式、风格不限，内容积极健康、向上，具有新意，并符合“生态龙江”前海教育杯摄影大赛主题。不接收其他各级影赛的获奖图片。</li>
				<li>4.作品必须注明作品主题、拍摄地点、作者姓名、通讯地址、邮政编码和联系电话。资料不齐，不做评选。</li>
				<li>5.凡向本次大赛投稿者，均视为同意本次大赛的所有规则。参赛作品所涉及的名誉权、肖像权、著作权等法律责任人均由作者本人负责，人民网及组委会不承担任何责任。</li>
				<li>6.本次投稿作品版权归人民网所有，任何单位与个人不得擅自使用，更不能用于除本次活动宣传外的商业行为。</li>
				<li>7.本活动最终解释权归人民网黑龙江频道所有。</li>
			</ul>
            <%-- The global progress information --%>
            <div class="span5 fileupload-progress fade" >
             	<div class="all" style="float:right;"></div>
                <!-- The global progress bar -->
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width:605px;">
                    <div class="bar" style="width:0%;"></div>
                </div>
                <%-- The extended global progress information --%>
                <div class="progress-extended">&nbsp;</div>
            </div>
        </div>
       
        <div class="fileupload-loading"></div>
        <br>
        <%-- The table listing the files available for upload/download --%>
        <table role="presentation" class="table table-striped"><tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></tbody></table>
 
</div>

<%-- modal-gallery is the modal dialog used for the image gallery --%>
<div id="modal-gallery" class="modal modal-gallery hide fade" data-filter=":odd">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>
        <h3 class="modal-title"></h3>
    </div>
    <div class="modal-body"><div class="modal-image"></div></div>
    <div class="modal-footer">
        <a class="btn modal-download" target="_blank">
            <i class="icon-download"></i>
            <span>下载</span>
        </a>
        <a class="btn btn-success modal-play modal-slideshow" data-slideshow="5000">
            <i class="icon-play icon-white"></i>
            <span>幻灯片</span>
        </a>
        <a class="btn btn-info modal-prev">
            <i class="icon-arrow-left icon-white"></i>
            <span>上一个</span>
        </a>
        <a class="btn btn-primary modal-next">
            <span>下一个</span>
            <i class="icon-arrow-right icon-white"></i>
        </a>
    </div>
</div>

<%-- The template to display files available for upload --%>
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td class="preview"><span class="fade"></span></td>
        <td class="name"><span>{%=file.name.substring(0,10)%}{% if ((file.name).length>10) { %}...{% } %}</span></td>
        <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
        {% if (file.error) { %}
            <td class="error" colspan="2"><span class="label label-important">{%=locale.fileupload.error%}</span> {%=locale.fileupload.errors[file.error] || file.error%}</td>
        {% } else if (o.files.valid && !i) { %}
            <td>
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
            </td>
            <td class="start">{% if (!o.options.autoUpload) { %}
                <button class="btn btn-primary">
                    <span>{%=locale.fileupload.start%}</span>
                </button>
            {% } %}</td>
        {% } else { %}
            <td colspan="2"></td>
        {% } %}
        <td class="cancel">{% if (!i) { %}
            <button class="btn btn-warning">
                <span>{%=locale.fileupload.cancel%}</span>
            </button>
        {% } %}</td>
    </tr>
{% } %}
</script>

<%-- The template to display files available for download --%>
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        {% if (file.error) { %}
            <td></td>
            <td class="name"><span>{%=file.name%}</span></td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            <td class="error" colspan="2"><span class="label label-important">{%=locale.fileupload.error%}</span> {%=locale.fileupload.errors[file.error] || file.error%}</td>
        {% } else { %}
            <td class="preview" style="width:57px;">{% if (file.thumbnail_url) { %}
               <img src="{%=file.thumbnail_url%}" width="57px" height="61px">
            {% } %}</td>
            <td class="name" colspan="3">
               <a href="javascript:preview('{%=file.file_type%}')">  {%=file.name.substring(0,15)%}{% if ((file.name).length>15) { %}...{% } %}</a>
            </td>
            <td class="size" ><span>上传成功</span></td>
        {% } %}
        <td class="delete" >
            <button class="btn btn-danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}">
                <span>{%=locale.fileupload.destroy%}</span>
            </button>
        </td>
    </tr>
{% } %}
</script>

<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/jquery.min.js"></script>
<%-- The jQuery UI widget factory, can be omitted if jQuery UI is already included --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/vendor/jquery.ui.widget.js"></script>
<%-- The Templates plugin is included to render the upload/download listings --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/tmpl.min.js"></script>
<%-- The Load Image plugin is included for the preview images and image resizing functionality --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/load-image.min.js"></script>
<%-- The Canvas to Blob plugin is included for image resizing functionality --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/canvas-to-blob.min.js"></script>
<%-- Bootstrap JS and Bootstrap Image Gallery are not required, but included for the demo --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/bootstrap-image-gallery.min.js"></script>
<%-- The Iframe Transport is required for browsers without support for XHR file uploads --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/jquery.iframe-transport.js"></script>
<%-- The basic File Upload plugin --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/jquery.fileupload.js"></script>
<%-- The File Upload file processing plugin --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/jquery.fileupload-fp.js"></script>
<%-- The File Upload user interface plugin --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/jquery.fileupload-ui.js"></script>
<%-- The localization script --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/locale.js"></script>
<%-- The main application script --%>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/main.js"></script>
<script type="text/javascript" src="<%=path%>/parts/JS/upload/js/cors/jquery.xdr-transport.js"></script>
</form>
</body> 
</html>
