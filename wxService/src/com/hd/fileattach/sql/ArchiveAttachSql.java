package com.hd.fileattach.sql;

import com.hd.SystemInterface.SystemInterFace;

public class ArchiveAttachSql implements SystemInterFace {

	public static final String INS_ARCHIVE_ATTACH = "insert into  tab_archive_attach(file_id ,file_name , file_real_name , file_type ,  create_time , file_path , file_archive_type, ftp_server_id  ,file_archive_id  ) values(?,?,?,?,?,?,?,?,?)";

	public static final String SEL_FILE_MES = "select * from tab_archive_attach where file_id=?";

	public static final String DEL_DATA = "delete from tab_archive_attach  where file_name=?";

	public static final String IMAGES_SEL = " select [file_id] ,[file_name] ,[file_real_name] ,[file_type] ,[create_time] ,[file_path] ,[file_archive_id]"
			+ " ,[ftp_server_id] ,[file_archive_type] ,file_title,file_addr,count(tv.id) as votes from tab_archive_attach taa"
			+ "  left join [tab_vote tv on taa.file_id=tv.pic_id group by file_id,[file_name] ,[file_real_name]"
			+ " ,[file_type] ,[create_time] ,[file_path],[file_archive_id] ,[ftp_server_id] ,[file_archive_type],file_title,file_addr  order by votes desc ";

	public static final String IMAGES_SEL_BY_USERID_PART1 = " select [file_id] ,[file_name] ,[file_real_name] ,[file_type] ,[create_time] ,[file_path] ,[file_archive_id],file_title,file_addr"
			+ " ,[ftp_server_id] ,[file_archive_type] ,count(tv.id) as votes from tab_archive_attach taa"
			+ "  left join tab_vote tv on taa.file_id=tv.pic_id ";
	public static final String IMAGES_SEL_BY_USERID_PART2 = " group by file_id,[file_name] ,[file_real_name]"
			+ " ,[file_type] ,[create_time] ,[file_path],[file_archive_id] ,[ftp_server_id] ,[file_archive_type],file_title,file_addr  order by votes desc ";

}
