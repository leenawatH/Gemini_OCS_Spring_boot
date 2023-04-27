package project.group_1.gemini.model;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.gemini.app.ocs.OCS;
import edu.gemini.app.ocs.model.ObservingProgram;
import edu.gemini.app.ocs.model.ObservingProgramConfigs;
import edu.gemini.app.ocs.model.ObservingProgramConfigs.CalibrationUnit;
import edu.gemini.app.ocs.model.ObservingProgramConfigs.FoldMirrorType;
import edu.gemini.app.ocs.model.ObservingProgramConfigs.LightType;
import edu.gemini.app.ocs.model.SciencePlan;
import edu.gemini.app.ocs.model.TelePositionPair;


@Service
public class Observing {
    OCS ocs = new OCS(false);

    public ObservingProgram createObservingProgram(Map<String, Object> body  , Integer id){
		System.out.println("\nCreate a new observing program");
        
        System.out.println("\n"+ocs.getAllSciencePlans());
        
        SciencePlan sp = ocs.getSciencePlanByNo(id);
  
		String opticsPrimary = body.get("opticsPrimary").toString();
		double fStop = Double.parseDouble(body.get("fStop").toString());
		double opticsSecondaryRMS = Double.parseDouble(body.get("opticsSecondaryRMS").toString());
		double scienceFoldMirrorDegree = Double.parseDouble(body.get("scienceFoldMirrorDegree").toString());
		FoldMirrorType scienceFoldMirrorType = ObservingProgramConfigs.FoldMirrorType.valueOf(body.get("scienceFoldMirrorType").toString());
		int moduleContent = Integer.parseInt(body.get("moduleContent").toString());
		CalibrationUnit calibrationUnit = ObservingProgramConfigs.CalibrationUnit.valueOf(body.get("calibrationunits").toString());
		LightType lightType = ObservingProgramConfigs.LightType.valueOf(body.get("lightTypes").toString());

		TelePositionPair[] telePositionPairs = new TelePositionPair[5];
		TelePositionPair telePositionPair1 = new TelePositionPair(Double.parseDouble(body.get("direction[0]").toString()), Double.parseDouble(body.get("degree[0]").toString()));
        telePositionPairs[0] = telePositionPair1;
        TelePositionPair telePositionPair2 = new TelePositionPair(Double.parseDouble(body.get("direction[1]").toString()), Double.parseDouble(body.get("degree[1]").toString()));
        telePositionPairs[1] = telePositionPair2;
        TelePositionPair telePositionPair3 = new TelePositionPair(Double.parseDouble(body.get("direction[2]").toString()), Double.parseDouble(body.get("degree[2]").toString()));
        telePositionPairs[2] = telePositionPair3;
        TelePositionPair telePositionPair4 = new TelePositionPair(Double.parseDouble(body.get("direction[3]").toString()), Double.parseDouble(body.get("degree[3]").toString()));
        telePositionPairs[3] = telePositionPair4;
        TelePositionPair telePositionPair5 = new TelePositionPair(Double.parseDouble(body.get("direction[4]").toString()), Double.parseDouble(body.get("degree[4]").toString()));
        telePositionPairs[4] = telePositionPair5;

		
        ObservingProgram op = ocs.createObservingProgram(sp, (String) opticsPrimary,
		fStop, opticsSecondaryRMS, scienceFoldMirrorDegree, scienceFoldMirrorType,
		moduleContent, calibrationUnit, lightType, telePositionPairs);

		  
        // validate the correctness of the observing program
        op.validateObservingCondition(op);
        // save the observing program into the OCS system
        ocs.saveObservingProgram(op);

        // display the observing programs of all the science plans.
		ArrayList<SciencePlan> sciencePlans = ocs.getAllSciencePlans();
        sciencePlans = ocs.getAllSciencePlans();
        System.out.println(sciencePlans);
       

		return op;
    }

	
}