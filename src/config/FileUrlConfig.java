package config;

/**
 * 文件操作路径配置（文件服务器）
 * @author gql
 *
 */
public class FileUrlConfig {

	/** 文件访问根地址（外网访问） */
	public static final String file_visit_url = "http://test.oksheng.com.cn/fileservice";
	
	/**  文件操作根地址（文件服务器，内网访问） */
	public static final String file_handle_url = "http://test.oksheng.com.cn/fileservice";

	/**  文件服务器操作凭证 */
	public static final String file_remote_token = "sljz@20170923@4.1@iqmkj#jnbttioioahbu.134nfgv";
	
	/** 文件服务器上传文件访问地址 */
	public static final String file_remote_upload_url = file_handle_url + "/fileService/upload";
	
	/** 文件服务器删除文件访问地址 */
	public static final String file_remote_delete_url = file_handle_url + "/fileService/delete";

	/** 首页广告图片目录 */
	public static final String file_remote_adImage_url ="adImage";

	/** 合作商家图片目录 */
	public static final String file_remote_systemBusinessesPartner_url ="systemBusinessesPartner";

	/** 公告图片目录 */
	public static final String file_remote_noticeImage_url ="noticeImage";

	/** 币种管理图片目录 */
	public static final String file_remote_transactionCyrrency_url = "transactionCyrrency";

	/** 实名认证，证件照目录 */
	public static final String file_remote_identificationImage_url ="identificationImage";

	/** 公告、热门默认图片标识 */
	public static final String notice_hotTopic_defaultImage ="1";

}
