package br.com.systec.taskflow.project.impl.converter;

import br.com.systec.taskflow.project.api.converter.ProjectCategoryConverter;
import br.com.systec.taskflow.project.api.model.ProjectCategory;
import br.com.systec.taskflow.project.api.vo.ProjectCategoryVO;
import br.com.systec.taskflow.project.impl.fake.ProjectFake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProjectCategoryConverterTest {

    @Test
    void whenConverterProjectCategoryVOToModel_thenReturnProjectCategory() {
        ProjectCategoryVO projectCategoryVO = ProjectFake.toFakeProjectCategoryVO();

        ProjectCategory projectCategoryConverted = ProjectCategoryConverter.toModel(projectCategoryVO);
        Assertions.assertThat(projectCategoryConverted).isNotNull();
        Assertions.assertThat(projectCategoryConverted.getId()).isEqualTo(projectCategoryVO.getId());
        Assertions.assertThat(projectCategoryConverted.getDescription()).isEqualTo(projectCategoryVO.getDescription());
    }

    @Test
    void whenConverterProjectCategoryToVO_thenReturnProjectCategoryVO() {
        ProjectCategory projectCategory = ProjectFake.toFakeProjectCategory();

        ProjectCategoryVO projectCategoryVOConverted = ProjectCategoryConverter.toVO(projectCategory);
        Assertions.assertThat(projectCategoryVOConverted).isNotNull();
        Assertions.assertThat(projectCategoryVOConverted.getId()).isEqualTo(projectCategory.getId());
        Assertions.assertThat(projectCategoryVOConverted.getDescription()).isEqualTo(projectCategory.getDescription());
    }
}
