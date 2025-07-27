package br.com.systec.taskflow.project.impl.converter;

import br.com.systec.taskflow.project.api.converter.ProjectStatusConverter;
import br.com.systec.taskflow.project.api.model.ProjectStatus;
import br.com.systec.taskflow.project.api.vo.ProjectStatusVO;
import br.com.systec.taskflow.project.impl.fake.ProjectFake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectStatusConverterTest {

    @Test
    void whenConverterProjectStatusVOToModel_thenReturnProjectStatus() {
        ProjectStatusVO projectStatusVO = ProjectFake.toFakeProjectStatusVO();

        ProjectStatus projectStatusConverted = ProjectStatusConverter.toModel(projectStatusVO);

        Assertions.assertThat(projectStatusConverted).isNotNull();
        Assertions.assertThat(projectStatusConverted.getId()).isEqualTo(projectStatusVO.getId());
        Assertions.assertThat(projectStatusConverted.getDescription()).isEqualTo(projectStatusVO.getDescription());
        Assertions.assertThat(projectStatusConverted.getName()).isEqualTo(projectStatusVO.getName());
        Assertions.assertThat(projectStatusConverted.getColor()).isEqualTo(projectStatusVO.getColor());
    }

    @Test
    void whenConverterProjectStatusToVO_thenReturnProjectStatusVO() {
        ProjectStatus projectStatus = ProjectFake.toFakeProjectStatus();

        ProjectStatusVO projectStatusVOConverted = ProjectStatusConverter.toVO(projectStatus);

        Assertions.assertThat(projectStatusVOConverted).isNotNull();
        Assertions.assertThat(projectStatusVOConverted.getId()).isEqualTo(projectStatus.getId());
        Assertions.assertThat(projectStatusVOConverted.getDescription()).isEqualTo(projectStatus.getDescription());
        Assertions.assertThat(projectStatusVOConverted.getName()).isEqualTo(projectStatus.getName());
        Assertions.assertThat(projectStatusVOConverted.getColor()).isEqualTo(projectStatus.getColor());
    }
}
