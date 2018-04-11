package com.rimidev.backing;

import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.controller.SurveyJpaController;
import com.rimidev.maxbook.entities.Survey;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 * Backing Bean to aid with the survey section of the home page
 * 
 * @author Philippe Langlois-Pedroso
 */
@Named("surveyBacking")
@RequestScoped
public class SurveyBacking implements Serializable {

    @Inject
    private SurveyJpaController surveyJpaController;
    
    private Survey survey;
    private String answer;
    private BarChartModel barModel;
    private int questionId = 1;
    private Logger logger = Logger.getLogger(ClientJpaController.class.getName());
    
    @PostConstruct
    public void init(){
        getSurvey();
        createBarModel();
        logger.info("BAR MODEL: " +barModel.getTitle());
    }
    
    public BarChartModel getBarModel(){
        return barModel;
    }
    
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
    
    private BarChartModel initBarModel(){
        BarChartModel barModel = new BarChartModel();
        ChartSeries results = new ChartSeries();
        results.setLabel("Results");
        results.set(survey.getOption1(), survey.getCount1());
        results.set(survey.getOption2(), survey.getCount2());
        results.set(survey.getOption3(), survey.getCount3());
        results.set(survey.getOption4(), survey.getCount4());
        barModel.addSeries(results);
        return barModel;
    }
    
    private BarChartModel createBarModel(){
        barModel = initBarModel();
        barModel.setTitle(survey.getQuestion());
        barModel.setLegendPosition("ne");
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Options");
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Responses");
        yAxis.setMin(0);
        yAxis.setMax(20);
        return barModel;
    }
}
