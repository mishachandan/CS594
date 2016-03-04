import java.io.IOException;
import java.util.List;

import eu.impact_project.iif.ws.generic.SoapInput;
import eu.impact_project.iif.ws.generic.SoapOperation;
import eu.impact_project.iif.ws.generic.SoapOutput;
import eu.impact_project.iif.ws.generic.SoapService;


public class WSDLMain {

	public static void main(String[] args) throws IOException {
		
		try{
		SoapService service = new SoapService("http://localhost:8080/MyFirstWebService/services/FirstWebService?wsdl");
		
		List<SoapOperation> allOps = service.getOperations();  
		System.out.println(allOps);
		
		for(SoapOperation temp : allOps){
			System.out.println(temp.getName());
		}
		
		System.out.println();
		System.out.println("Inputs");
		
		SoapOperation op = service.getOperation("addTwoNumbers");
		
		List<SoapInput> allInputs = op.getInputs();  
		
		for(SoapInput temp : allInputs){
			System.out.println(temp.getName());
			SoapInput in = temp;
			String name = in.getName();  
			in.setValue("1");
		}
		
		List<SoapOutput> outs = null;
		for(int i = 0; i<10; i++){
		long startTime = System.nanoTime();
		outs = op.execute("","");
		long endTime = System.nanoTime();
		long estimatedTime = endTime- startTime;
		double estTime = estimatedTime*(Math.pow(10, -9));
		System.out.println(estTime + " Secs");
		}
		
		for (SoapOutput out : outs) {  
            System.out.println(out.getValue());
            System.out.println();
            }
		}catch(NullPointerException e){
			
		}
		
	}

}
