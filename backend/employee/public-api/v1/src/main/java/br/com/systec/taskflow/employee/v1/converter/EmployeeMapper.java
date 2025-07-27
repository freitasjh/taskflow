package br.com.systec.taskflow.employee.v1.converter;

import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import br.com.systec.taskflow.employee.v1.dto.EmployeeInputDTO;
import br.com.systec.taskflow.employee.v1.dto.EmployeeResponseDTO;

public class EmployeeMapper {

    private EmployeeMapper() {
        // Private constructor to prevent instantiation
    }

    public static EmployeeVO toVO(EmployeeInputDTO dto) {
        if (dto == null) {
            return null;
        }

        EmployeeVO vo = new EmployeeVO();

        vo.setId(dto.getId());
        vo.setName(dto.getName());
        vo.setEmail(dto.getEmail());
        vo.setPhone(dto.getPhone());
        vo.setCellphone(dto.getCellphone());
        vo.setFederationId(dto.getFederalId());
        vo.setUsername(dto.getUsername());
        vo.setPassword(dto.getPassword());
        vo.setDepartament(dto.getDepartament());

        return vo;
    }

    public static EmployeeResponseDTO toResponseDTO(EmployeeVO vo) {
        if (vo == null) {
            return null;
        }

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(vo.getId());
        dto.setName(vo.getName());
        dto.setEmail(vo.getEmail());
        dto.setPhone(vo.getPhone());
        dto.setCellphone(vo.getCellphone());
        dto.setFederalId(vo.getFederationId());

        return dto;
    }

    public static EmployeeInputDTO toInputDTO(EmployeeVO employeeVO) {
        if (employeeVO == null) {
            return null;
        }

        EmployeeInputDTO dto = new EmployeeInputDTO();

        dto.setId(employeeVO.getId());
        dto.setName(employeeVO.getName());
        dto.setEmail(employeeVO.getEmail());
        dto.setPhone(employeeVO.getPhone());
        dto.setCellphone(employeeVO.getCellphone());
        dto.setFederalId(employeeVO.getFederationId());
        dto.setUsername(employeeVO.getUsername());
        dto.setDepartament(employeeVO.getDepartament());

        return dto;
    }
}
