package com.rimidev.backing;

import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.controller.SurveyJpaController;
import com.rimidev.maxbook.entities.Survey;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 * Backing Bean to aid with the survey section of the home page
 * 
 * @author Philippe Langlois-Pedroso
 */
@Named("surveyBacking")
@SessionScoped
public class SurveyBacking implements Serializable {

    @Inject
    private SurveyJpaController surveyJpaController;
    
    private Survey survey;
    private String answer;
    private int questionId = 1;
    private Logger logger = Logger.getLogger(ClientJpaController.class.getName());
    
    /**
     * Survey created if it does not exist.
     *
     * @return
     */
    public Survey getSurvey() {
        if (survey == null) {
            logger.info("survey was null");
            survey = surveyJpaController.findSurvey(questionId);
        }
        return survey;
    }

    /**
     * Save the survey answer to the db
     *
     * @returns
     * @throws Exception
     */
    public void submit() throws Exception{
        logger.info("SUBMIT : " +survey.getQuestion());
        switch(answer){
            case "1":
                survey.setCount1(survey.getCount1() +1);
                break;
            case "2":
                survey.setCount2(survey.getCount2() +1);
                break;
            case "3":
                survey.setCount3(survey.getCount3() +1);
                break;
            case "4":
                survey.setCount4(survey.getCount4() +1);
                break;
        }
        surveyJpaController.edit(survey);
    }

    public String getAnswer() {
        logger.info("getAnswer() : " +answer);
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        logger.info("setAnswer() : " +this.answer);
    }
}
