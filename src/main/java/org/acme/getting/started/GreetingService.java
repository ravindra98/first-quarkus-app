package org.acme.getting.started;

import javax.enterprise.context.ApplicationScoped;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.*;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.*;
import java.text.BreakIterator;
import java.util.Vector;
import java.util.Locale;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
@ApplicationScoped
public class GreetingService {

    public JSONArray greeting(String sentence){
        JSONArray arrayy=new JSONArray();
        try{
            GreetingService g=new GreetingService();
             arrayy= g.test(sentence);
             return arrayy;
        }
        catch(IOException e){
            System.out.println(e);
        }
        return arrayy;
    }


    DoccatModel model;
    static int positive = 0;
    static int negative = 0;
static float abuserate=0.2F;

public JSONArray test(String parameter) throws IOException{

//String parameter="You blood shit of piece. What the heck is wrong with you?";
     String[] sentences=this.getSentences(parameter);   
             boolean flag=false;
   
         String strr = "";
         for(String sentence : sentences )
         {
             String[] temp=sentence.split("\\.");
             
             for(String emp : temp){
                 //String[] tempo=emp.split(".");
                //for(String empo:tempo)
                 //{
                 strr+="##cyberlull"+emp;
                 
             //}
         }}
             
         String[] originalsentences=strr.split("##cyberlull");
         //response.setHeader("perabuse",Gateway.abuserate+"%");
         //response.setHeader("parabuse", GreetingService.abuserate+"%");
         System.out.println(GreetingService.abuserate+"%");
         
         
        String[] result = this.parseAndDetect(originalsentences);
        
        JSONArray resultinjson=this.convertToJson(originalsentences,result);
        
        //String escape = JSONObject.escape(resultinjson);
        
         //JSONParser parser = new JSONParser();
         //JSONArray js=new JSONArray();
//         s.add(resultinjson);
        //JSONArray json = (JSONArray) parser.parse(escape);
       // System.out.println("result in json:"+js.toJSONString());
      if(flag==false){
System.out.println(resultinjson);
        }
        else{
            System.out.println(GreetingService.abuserate+"");
}
      
      return resultinjson;
}
    public JSONArray convertToJson(String[] sentences,String[] results)
    {//JsonArray jsonaray=new JsonArray();
        
      int abusecount=0;
        
        //creating jsonarray to store json objects
        JSONArray js=new  JSONArray();
        int total=sentences.length;
        for(int i=0;i<total;i++)  
        {  if(sentences[i].trim().length()>=3){
// creating jsonObject for each <sentence result> pair
            JSONObject obj=new JSONObject();
            try{// attach key value pairs with jsonobject
                obj.put("sentence",sentences[i] );
                obj.put("result",results[i]);
                System.out.println("result:"+results[i]);
                   if(results[i].contains("0")){
                       abusecount=abusecount+1;
                       System.out.println("abuserate increased"+abusecount);
                   }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //append jsonobject to jsonarray
            js.add(obj);
        }
        }
        JSONObject finalObject=new JSONObject();
        //finalObject.put("success", j);
        //System.out.println("js"+js);
        this.abuserate=(abusecount*100)/total;
        System.out.println("total:"+total+"\n abusecount:"+abusecount+"\n abuse rate:"+this.abuserate);
//JsonObject jso//n = new JsonObject();

//json.addProperty("sentence", sentences[i]);
//json.addProperty("result", results[i]);

//jsonaray.add(json);
return js;
        }
public String[] parseAndDetect(String[] args) throws IOException {
        
        
        this.trainModel();
       
        //String[] sentences;           sentences=this.getSentences(args);
        //String pqrs=args;
        
        //String[] qss=pqrs.split("<br>");
        String[] result=new String[args.length];
        for(int i=0;i<args.length;i++)
        {
        int q=this.classifyNewTweet(args[i]);
       result[i]=((q==0) ? "0" : "1");
            //result[i]=sentence;
        }
        return result;
       
    }

    public void trainModel() {
        InputStreamFactory dataIn;
        ClassLoader classLoader = new GreetingService().getClass().getClassLoader();
        System.out.println(classLoader.getResourceAsStream("META-INF/resources/input.txt"));

          //InputStream dataIn = this.getClass().getClassLoader().getResourceAsStream("/input.txt");
        try {            
        dataIn = new InputStreamFactory() {
            public InputStream createInputStream() throws IOException {
                return this.getClass().getClassLoader().getResourceAsStream("META-INF/resources/input.txt");
            }
        };
                    
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new  DocumentSampleStream(lineStream);
            // Specifies the minimum number of times a feature must be seen
            
             TrainingParameters params = new TrainingParameters();
 params.put(TrainingParameters.ITERATIONS_PARAM, 50);
 params.put(TrainingParameters.CUTOFF_PARAM, 1);

            model = DocumentCategorizerME.train("en",sampleStream,
                                params,                            
                    new DoccatFactory());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           
            }
        }
    
  public int classifyNewTweet(String tweet)  {
        //String[] tokens= new String[] {tweet};
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        double[] outcomes = myCategorizer.categorize(new String[]{tweet," "});
        String category = myCategorizer.getBestCategory(outcomes);


        System.out.print("-----------------------------------------------------\nTWEET :" + tweet + " ===> ");
        if (category.equalsIgnoreCase("1")) {
            System.out.println("POSITIVE");
            return 1;
        } else {
            System.out.println(" NEGATIVE ");
            return 0;
        }
    

    }

    
    private String[] getSentences(String args) {
   Vector v=new Vector();
 BreakIterator border = BreakIterator.getSentenceInstance(Locale.US);

    border.setText(args);
int start = border.first();
//iterate, creating sentences out of all the Strings between the given boundaries
for (int end = border.next(); end != BreakIterator.DONE; start = end, end = border.next()) {
   v.add(args.substring(start,end));
}
String[] sentences=new String[v.size()];
for(int i=0;i<v.size();i++)
{
    sentences[i]=(String) v.elementAt(i);
    //sentences[i]=sentences[i].toLowerCase();
}
return sentences;
    }

}
