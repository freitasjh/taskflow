package br.com.systec.taskflow.workflow.api.converter;

import br.com.systec.taskflow.workflow.api.model.Transition;
import br.com.systec.taskflow.workflow.api.vo.TransitionVO;

import java.util.ArrayList;
import java.util.List;

public class TransitionConverter {

    private TransitionConverter(){}

    public static Transition toModel(TransitionVO transitionVO) {
        Transition transition = new Transition();

        transition.setId(transitionVO.getId());
        transition.setFromStatus(StatusConverter.toModel(transitionVO.getFromStatus()));
        transition.setToStatus(StatusConverter.toModel(transitionVO.getToStatus()));

        return transition;
    }

    public static TransitionVO toVO(Transition transition) {
        TransitionVO transitionVO = new TransitionVO();

        transitionVO.setId(transition.getId());
        transitionVO.setFromStatus(StatusConverter.toVO(transition.getFromStatus()));
        transitionVO.setToStatus(StatusConverter.toVO(transition.getToStatus()));

        return transitionVO;
    }

    public static List<Transition> toList(List<TransitionVO> listOfTransition) {
        if (listOfTransition == null) {
            return new ArrayList<>();
        }

        return listOfTransition.stream().map(TransitionConverter::toModel).toList();
    }

    public static List<TransitionVO> toListVO(List<Transition> listOfTransition) {
        if (listOfTransition == null) {
            return new ArrayList<>();
        }

        return listOfTransition.stream().map(TransitionConverter::toVO).toList();
    }
}
