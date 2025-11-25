package com.hkha.ea.dao.assess;

import java.util.List;

import com.hkha.ea.domain.EaBatch;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.dto.assess.AssignOfficerDto;
import com.hkha.ea.dto.assess.AssignofficerPartialDto;
import com.hkha.ea.dto.assess.Pem001RDto;

public interface AssignOfficerDAO {

	public void saveAction(String currentUsername, List<String> appraisees,AssignOfficerDto assignOfficerDto, EaReport rp, Pem001RDto pemModel, AssignofficerPartialDto p)throws Exception;

	public void updateAction(List<Long> appraisees, AssignOfficerDto assignOfficerDto, AssignofficerPartialDto p,
			EaReport rp, EaBatch batch, String role,String mode)throws Exception;

}
