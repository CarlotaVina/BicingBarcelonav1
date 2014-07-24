package bicing.pig;


import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Cluster;
import org.apache.pig.ExecType;
import org.apache.pig.PigServer;
import org.apache.pig.backend.hadoop.hbase.HBaseStorage;

import org.apache.commons.io.IOUtils;


public class PigEstadisiticasFechasBikes {
	
	private static Configuration conf  = null;        
	private static Configuration conf1  = null; 
	private static Configuration conf2  = null; 
	public static final String tableName="BikesDates";
	public static final String columnFamility1="total";
	final static String Name = "export";
	private static HBaseAdmin admin = null;
	
	public static String cargarEstadisticas() {
		try {
			
	   System.out.println("en main");  
	   Properties props = new Properties();
	   props.setProperty("fs.default.name","hdfs://localhost.localdomain:8020");
	   props.setProperty("mapred.job.tracker","localhost.localdomain:8021");
	  
		   PigServer pigServer = new PigServer("local"); 
		    
		   System.out.println("antes de register");
		   
		  // pigServer.registerJar("/usr/lib/hbase/lib/zookeeper.jar");
		  // pigServer.registerJar("/usr/lib/hbase/lib/protobuf-java-2.4.0a.jar");
		  // pigServer.registerJar("/usr/lib/pig/pig.jar");
		  // pigServer.registerJar("/usr/lib/hbase/lib/guava-11.0.2.jar");
		  // pigServer.registerJar("/usr/lib/hbase/hbase.jar");
	
	   
	   
	   
	   
	   
	   
	 //  pigServer.registerJar("/usr/lib/hadoop/client-0.20/hadoop-core.jar");
	 //  pigServer.registerJar("/usr/lib/hadoop-0.20-mapreduce/hadoop-core.jar");
	//   pigServer.registerJar(" /usr/lib/hbase/hbase-0.94.6-cdh4.4.0-security.jar");7
	   
	   System.out.println("despues de register");
	  
	   
	   
		
		System.out.println("antes de runMyQuery ");
		

		
		runMyQuery(pigServer,"hbase://DatosBicing");
		
		System.out.println("despues de runMyQuery");
		
		return new String("1");
		
	}
    catch (IOException e) {
    	System.out.println("exception 1 pig Server");
         e.printStackTrace();
    	return new String("0");
    }
    catch(Exception e ){
    	System.out.println("exception 2 pig Server");
    	
    	e.printStackTrace();
    	return new String("0");
    }

}
public static void runMyQuery(PigServer pigServer, String inputFile) throws IOException {
	
	String s;
	System.out.println("inpuFile "+inputFile);
	
	//pigServer.setBatchOn();

	
		try {
			Class exampleClass = Class.forName("org.apache.pig.backend.hadoop.hbase.HBaseStorage");
			
			System.out.println("clase encontrada");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("despues de clase");
		
		
	    	    
    
						
	 
	System.out.println("en registerQuery ");
	
	/*pigServer.registerQuery("source = load '"+ inputFile + "' USING  org.apache.pig.backend.hadoop.hbase.HBaseStorage( " +
	                        "'datos:bikes', '-loadKey=true')"+
			                "as (id1:bytearray);");*/
	
	pigServer.registerQuery("source = load '"+ inputFile + "' USING  org.apache.pig.backend.hadoop.hbase.HBaseStorage( " +
    "'datos:bikes', 'datos:timestamp '-loadKey true')"+
    "as (id1:int, bikes:int, timestamp:int  ) ;");
	
	Path output = new Path("/home/cloudera/proyecto/consulta2");
	
	System.out.println("despues de registrar query");
	conf = new Configuration(true);
	System.out.println("despues de new configuration ");
	
	System.out.println("despues de filesystem");
	conf.addResource(new Path("/etc/hadoop/conf.cloudera.hdfs1/core-site.xml"));
	 
	 System.out.println("despues de anadir fichero a conf");
	   //conf1.addResource(new Path("/home/hadoop/conf/core-site.xml"));
	  //hdfs = FileSystem.get(conf);
	 FileSystem hdfs  = FileSystem.get(conf);
	   System.out.println("hdfs "+hdfs);

	    //System.out.println("propiedad "+conf.getRaw("fs.default.name"));
	   
	// System.out.println("propiedad "+conf.getRaw("fs.default.name"));
	   
	  //String filePathDirectory="/user/cloudera/Bicing1/part-m-00000";
	 String filePathDirectory="hdfs://localhost.localdomain:8020/user/cloudera/consultaBikesFechas";
	 //String filePathDirectory="home/cloudera/consultaBicing1";
		   //Path path = new Path(filePathString);
	   Path path=new Path(filePathDirectory);
	   
	   hdfs.delete(path);
	   System.out.println("despues de new path");
	   
	   
   // pigServer.store("source","/home/cloudera/proyecto/consulta1");
	 //pigServer.store("source","hdfs://localhost:8020/user/cloudera/consultaBicing");
	 //pigServer.store("source","hdfs://localhost:8020/user/cloudera/consultaBicing1");
	   pigServer.store("source",path.toString());
	//hdfs://localhost:8020/user/cloudera/Bicing1
   
   System.out.println("despues de salvar query ");
   
      
	    
   
  // conf.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
   //conf1.addResource(new Path("/home/hadoop/conf/core-site.xml"));
   
   
  //String filePathDirectory="/user/cloudera/Bicing1/part-m-00000";
    System.out.println("despues de new path "+path);
   
   FileStatus [] dir = hdfs.listStatus(path);
   
   for (FileStatus fileStatus : dir)  {
   	
   	System.out.println(fileStatus.getPath() +  " " + fileStatus.getOwner() + " "+fileStatus.getLen());
   	
   	String s1  = fileStatus.getPath().getName();
   	
   	if (s1.contains("part-m-00000")) {
   		
   		  System.out.println("estoy en part-m-00000" );
   		  hdfs.rename(fileStatus.getPath(),  new Path("hdfs://localhost.localdomain:8020/user/cloudera/consultaBikesFechas/numerobicisFechas"));
   		  
   		 //FileSystem fs = FileSystem.get(new Configuration(true));
   		 
   		 System.out.println("despues de renombrar");
   		 
   	}
   	
   	  
   }
   System.out.println("hdfs close antes");
   hdfs.close();
System.out.println("hdfs close despues");
   	
   	   	 System.out.println("primer paso terminado");
   	   	 
   	  conf = new Configuration(true);
  	System.out.println("despues de new configuration ");
  	
  	System.out.println("despues de filesystem");
  	conf.addResource(new Path("/etc/hadoop/conf.cloudera.hdfs1/core-site.xml"));
  	 
  	 System.out.println("despues de anadir fichero a conf");
  	   //conf1.addResource(new Path("/home/hadoop/conf/core-site.xml"));
  	  //hdfs = FileSystem.get(conf);
  	  hdfs  = FileSystem.get(conf);
  	   System.out.println("hdfs "+hdfs);
   	   	 //String filePathString="hdfs://localhost.localdomain:8020/user/cloudera/consultaBicing1/numerobicis1";
   	     String filePathString="hdfs://localhost.localdomain:8020/user/cloudera/consultaBikesFechas/numerobicisFechas";
   	   	 Path path1 = new Path(filePathString);
   	   	 
   	    conf1 = HBaseConfiguration.create();
		    admin = new HBaseAdmin(conf1);
		    
		    if (admin.tableExists(tableName)) {
		    	admin.disableTable(tableName);
		    	admin.deleteTable(tableName);
		    }
		    
		    HTableDescriptor desc = new HTableDescriptor(tableName);
		    HColumnDescriptor coldef1 = new HColumnDescriptor(columnFamility1);
		    coldef1.setMaxVersions(300);
		    desc.addFamily(coldef1);
		    admin.createTable(desc);
		    
		    HTable tabla = new HTable(conf1,tableName);
		    
   	   	 System.out.println("path Bikes Fecha");
   	  //conf2 = new Configuration(true);
   	  //conf2.addResource(new Path("/etc/hadoop/conf.cloudera.hdfs1/core-site.xml"));
 	 
 	 //System.out.println("despues de anadir fichero a conf2");
 	   //conf1.addResource(new Path("/home/hadoop/conf/core-site.xml"));
 	  //hdfs = FileSystem.get(conf);
 	 //FileSystem hdfs1  = FileSystem.get(conf2);
 	   //System.out.println("hdfs2 "+hdfs1);
   	    //String filePathString1="hdfs://localhost.localdomain:8020/user/cloudera/consultaBicing1/numerobicis1";
	   	// Path path2= new Path(filePathString1);
	   	 //System.out.println("despues de path 2 ");
	   	 
   	   	 FileStatus [] dir1 = hdfs.listStatus(path1); 
   	   	 
   	   	 System.out.println("antes de bucle filestatus");
   	   	 
   	   	 for (FileStatus fileStatus1 :dir1) {
   	   	 
   	   		 
   	   		 System.out.println("dentro de for filestatus");    
   	   	     FSDataInputStream in = hdfs.open(fileStatus1.getPath());
   	   	     
   	   	     System.out.println("antes de read");
   	   	 
   	   	     byte[] buffer = new byte[1024];
   	   	     in.read(buffer);
   	   	     in.seek(0);
   	   	 
   	   	     List<String> lines = IOUtils.readLines(in);
   	   	 
   	   	    for (String cadena :lines) {
   	   		 String cadena1 = cadena + "\n";
   	   		 //System.out.println("cadena1 "+cadena1);
   	   		 
   	   		 int pos = cadena1.indexOf(" ", 1);
   	   		 //System.out.println(" pos "+pos);
   	   		 
   	   		 int l = cadena1.length();
   	   		 
   	   		 //System.out.println("longitud l "+l);
   	   	int pos1 = cadena1.indexOf("\b", 1);
	   		 //System.out.println(" pos1 "+pos1);
	   		 
	   		 
   	   		 
   	     	String[] valores = cadena1.toString().split(" ");
   	     	
   	     	String[] valores1 = new String[2];
   	     	
   	     	for (String valor: valores){
   	     	       // System.out.println("valor antes "+valor);
   	     		    valor = valor.trim();
   	     		   // System.out.println("valor "+valor);
   	     		    int l1 = valor.length();
   	     		    int pos2 = valor.indexOf(" ", 1);
   	     	     	int contador = 0;
   	     	     	valores1[contador] = "";
   	     	     	
   	     		    for (int i=0;i<l1;i++){
   	     		    	char c = valor.charAt(i);
   	     		        String s3 = c + "";
   	     		        int i1 = s3.compareTo(" ");
   	     		       // System.out.println(" s3 "+s3+" i1 "+i1);
   	     		        
   	     		    	if (i1<0){
   	     		    		//System.out.println("encontrado blanco contador antes "+contador);
   	     		    		contador= contador + 1;
   	     		    	    valores1[contador] = "";
   	     		    	//System.out.println("encontrado blanco contador despues  "+contador);
   	     		    		
   	     		    	}
   	     		    	else {
   	     		    	//System.out.println("valores1[contador] antes  "+valores1[contador]);
   	     		    		valores1[contador] = valores1[contador] + c + "";
   	     		    		//System.out.println("valores1[contador]  despues  "+valores1[contador]);
   	     		    	}
   	     		    	  	     		    	
   	     		    }
   	     		    String rowkey = valores1[0];
   	     		    //System.out.println("final valores1[1] "+valores1[0]+"valores[2] "+valores1[1]);
   	     		    //System.out.println("l1 "+l1+" pos2 "+pos2);
   	     		    if (valores1[0]!=null && valores1[1]!=null) {
   	     
   	     		     System.out.println("anadiendo en tabla ");
   	     		  
   	     		    Calendar c1 = Calendar.getInstance();
   	     		    String dia = Integer.toString(c1.get(Calendar.DATE));
   	     		    String mes = Integer.toString(c1.get(Calendar.MONTH));
   	     		    String annio = Integer.toString(c1.get(Calendar.YEAR));
   	     		    
   	     		    String hora = Integer.toString(c1.get(Calendar.HOUR_OF_DAY));
   	     		    String minute = Integer.toString(c1.get(Calendar.MINUTE));
   	     		    String second = Integer.toString(c1.get(Calendar.SECOND));
   	     		    String milisecond = Integer.toString(c1.get(Calendar.MILLISECOND));
   	     		    
   	     		    String date = dia + mes + annio + hora + minute + second + milisecond;
   	     		    
   	     		    String timestamp = date;
   	     		    
   	     		   
   	     		    System.out.println("antes de put ");
   	     		    Put put1 = new Put(Bytes.toBytes(rowkey), Long.valueOf(timestamp));
   	     		    System.out.println("despues de put ");
   	     		    
   	     		    put1.add(Bytes.toBytes("total"), Bytes.toBytes("totalbicis"), Bytes.toBytes(valores1[1]));
   	     		    
   	     		    System.out.println("antes de tabla put");
   	     		    tabla.put(put1);
   	     		    
   	     		    System.out.println("despues de tabla put ");
   	     		    
   	     		    
   	     		    } 
   	     		    
   	     		    
   	     	}
   	   		 
   	   		
   	   		 
   	   		 
   	   	 }
   	   	 
   	   	 System.out.println("despues de tabla close");
   	   	 tabla.close();
   	   	 System.out.println("antes de in close");
   	   	 in.close();
   	   	 System.out.println("despues de in close");
   	   	 
   	   	 
   	   	 }
   	   	 
   	   	 System.out.print("final 1");
   	}
   
      	 
   	 
   
public static void main(String[] args) {
	   System.out.println("antes de process builder");
	   
	  PigEstadisiticasFechasBikes pg = new PigEstadisiticasFechasBikes();
	  pg.cargarEstadisticas();
	   
}	                        
			
   
}