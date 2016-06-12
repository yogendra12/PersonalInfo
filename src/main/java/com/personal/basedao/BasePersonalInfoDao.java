
package com.personal.basedao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.personal.model.PersonalInfo;


public class BasePersonalInfoDao{

@Autowired public JdbcTemplate jdbcTemplate;

 
	public final String INSERT_SQL = "INSERT INTO personal_info( roll_no, password, name, contact_no, email, place_of_living, country, job_info, spouse_name, spouse_job) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 





	/* this should be conditional based on whether the id is present or not */
	@Transactional
	public void save(final PersonalInfo personalInfo) 
	{
	if(personalInfo.getId() == 0)	{

	KeyHolder keyHolder = new GeneratedKeyHolder();
	int update = jdbcTemplate.update(
			new PreparedStatementCreator() {
					public PreparedStatement 
					createPreparedStatement(Connection connection) throws SQLException {
	
					PreparedStatement ps =
									connection.prepareStatement(INSERT_SQL,new String[]{"id"});
	ps.setString(1, personalInfo.getRollNo());
ps.setString(2, personalInfo.getPassword());
ps.setString(3, personalInfo.getName());
ps.setString(4, personalInfo.getContactNo());
ps.setString(5, personalInfo.getEmail());
ps.setString(6, personalInfo.getPlaceOfLiving());
ps.setString(7, personalInfo.getCountry());
ps.setString(8, personalInfo.getJobInfo());
ps.setString(9, personalInfo.getSpouseName());
ps.setString(10, personalInfo.getSpouseJob());

							return ps;
						}
				},
				keyHolder);
				
				Number unId = keyHolder.getKey();
				personalInfo.setId(unId.intValue());
				

		}
		else
		{

			String sql = "UPDATE personal_info  set roll_no = ? ,password = ? ,name = ? ,contact_no = ? ,email = ? ,place_of_living = ? ,country = ? ,job_info = ? ,spouse_name = ? ,spouse_job = ?  where id = ? ";
	
			jdbcTemplate.update(sql, new Object[]{personalInfo.getRollNo(),personalInfo.getPassword(),personalInfo.getName(),personalInfo.getContactNo(),personalInfo.getEmail(),personalInfo.getPlaceOfLiving(),personalInfo.getCountry(),personalInfo.getJobInfo(),personalInfo.getSpouseName(),personalInfo.getSpouseJob(),personalInfo.getId()});
		}
	}
		
		@Transactional
		public void delete(int id) {
			String sql = "DELETE FROM personal_info WHERE id=?";
			jdbcTemplate.update(sql, new Object[]{id});
		}
		

	 public PersonalInfo getById(int id) {
			String sql = "SELECT * from personal_info where id = ? ";
			List<PersonalInfo> retlist = jdbcTemplate.query(sql,
			new Object[]{id},
			ParameterizedBeanPropertyRowMapper.newInstance(PersonalInfo.class));
			if(retlist.size() > 0)
				return retlist.get(0);
			return null;
		}

	

}
