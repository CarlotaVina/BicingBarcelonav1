package bicing.hbase;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapred.TableInputFormat;
import org.apache.hadoop.hbase.mapred.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.Export;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
 
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Progressable;

import org.apache.hadoop.fs.Path;

import org.jdom2.*;



public class Generadortabla {

	StringBuilder responseBuilder = new StringBuilder();

	public static final String tableName="DatosBicing";
	private static final byte[] TABLE_NAME = Bytes.toBytes(tableName);
	public static final String columnFamility1 ="datos";
	private static final byte[] CF = Bytes.toBytes(columnFamility1);
	final static String Name = "export";
	
	private Scan s = null;
	
	private ResultScanner scanner;

	String csvFile = "/home/cloudera/proyecto/datosBicing.csv";
	public static final String pathTabla = "/Bicing1";
	public static final String nuevoNombre = "/datosBicing";
	public static final String viejoNombre = "/Bicing1";
	//public static final String pathTabla = "hdfs://localhost:8020/user/cloudera/Bicing1";
	//public static final String nuevoNombre = "hdfs://localhost:8020/user/cloudera/datosBicing";
	//public static final String viejoNombre = "hdfs://localhost:8020/user/cloudera/Bicing1/part-m-00000";
	//public static final String viejoNombre = "hdfs://localhost:8020/user/cloudera/Bicing1";
	//public static final String viejoNombre = "hdfs://localhost.localdomain:8020/user/cloudera/Bicing1";

	final static String NAME = "export";
	BufferedReader br = null;

	String line = "";
	String cvsSplitBy = ",";

	Long updateTimeL = 0L;
	Date fechamvto = null;
	Long fechamvtoL = 0L;
	String fechamvtoS = null;
	Long fechamvtoL1 = 0L;


	HTable tabla = null;
	private static Configuration conf = null;                                                        

	private HBaseAdmin admin = null;
	AggregationClient aggregationClient = null;
	Configuration configuration = null;
	Scan scan = null;
	long rowCount = 0;
	long rowCount1 = 0;
	long putCount = 0;
	private Generadortabla() throws MasterNotRunningException, ZooKeeperConnectionException {


		this.conf = HBaseConfiguration.create();
		this.admin = new HBaseAdmin(conf);


	}
	private void  cargarXML() 
	{

		try {
			URL url = new URL("http://wservice.viabicing.cat/getstations.php?v=1");
			URLConnection conn =url.openConnection();
			HttpURLConnection httpConn;
			httpConn = (HttpURLConnection)conn;
			BufferedReader rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			String line;
			int contador = 1;
			String[] valoreslinea = new String[10];
			Integer contadoretiquetas = 0;
			boolean dividir = false;
			
			
			
			while ((line = rd.readLine()) != null)
			{



				{
					dividir = false;
					contador = contador + 1;
					if (line.contains("updatetime")) {
						line =line.replace("CDATA", "");
						line =line.replace("!", "");
						line =line.replace("]]", "");
						line =line.replace("[[", "");
						String[] lineaupdatetime = line.split("><");
						updateTimeL = new Long(lineaupdatetime[1]);
						updateTimeL = updateTimeL* 1000;
						fechamvto = new Date(updateTimeL);
						SimpleDateFormat dateformatDDMMYYYY = new SimpleDateFormat("ddMMyyyyHHmmss");

						StringBuilder nowDDMMYYYY = new StringBuilder(dateformatDDMMYYYY.format(fechamvto));
						fechamvtoS = nowDDMMYYYY.toString();
						fechamvtoL = Long.valueOf(fechamvtoS);
						fechamvtoL1 = fechamvtoL;


					}
					if (line.contains("street")) {
						line =line.replace("CDATA", "");
						line =line.replace("![[", "");
						line =line.replace("]]", "");
						dividir = true;
					}
					String[] datoslinea =  line.split(">");
					if (datoslinea.length == 2) {
						dividir = true;
					}

					if (dividir){
						String[] datoselemento2 =  datoslinea[0].split("<");
						String[] valorelemento2 = datoslinea[1].split("<");

						if (datoselemento2[1].equals("id")) {
							valoreslinea[0] = valorelemento2[0];
							contadoretiquetas = contadoretiquetas + 1;

						}

						if (datoselemento2[1].equals("lat")) {
							valoreslinea[1] = valorelemento2[0];
							contadoretiquetas = contadoretiquetas + 1;
						}

						if (datoselemento2[1].equals("long")) {
							valoreslinea[2] = valorelemento2[0];
							contadoretiquetas = contadoretiquetas + 1;
						}

						if (datoselemento2[1].equals("street")) {
							valoreslinea[3] = valorelemento2[1];
							contadoretiquetas = contadoretiquetas + 1;
						}

						if (datoselemento2[1].equals("height")) {
							valoreslinea[4] = valorelemento2[0];
							contadoretiquetas = contadoretiquetas + 1;
						}

						if (datoselemento2[1].equals("streetNumber")) {
							valoreslinea[5] = valorelemento2[0];
							contadoretiquetas = contadoretiquetas + 1;
						}

						if (datoselemento2[1].equals("nearbyStationList")) {
							valoreslinea[6] = valorelemento2[0];
							contadoretiquetas = contadoretiquetas + 1;
						}

						if (datoselemento2[1].equals("status")) {
							valoreslinea[7] = valorelemento2[0];
							contadoretiquetas = contadoretiquetas + 1;
						}

						if (datoselemento2[1].equals("slots")) {
							valoreslinea[8] = valorelemento2[0];
							contadoretiquetas = contadoretiquetas + 1;

						}

						if (datoselemento2[1].equals("bikes")) {
							valoreslinea[9] = valorelemento2[0];
							contadoretiquetas = contadoretiquetas + 1;


						}
						if (contadoretiquetas==10) {



							contadoretiquetas=0;

							String rowkey = valoreslinea[0];
//							try {
//								rowCount = aggregationClient.rowCount(TABLE_NAME,null,scan);
//							} catch (Throwable e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							System.out.println(" antes rowCount "+rowCount);

							Put put1 = new Put(Bytes.toBytes(rowkey),fechamvtoL);

							

							put1.add(Bytes.toBytes("datos"),Bytes.toBytes("lat"),Bytes.toBytes(valoreslinea[1]));
							put1.add(Bytes.toBytes("datos"),Bytes.toBytes("long"),Bytes.toBytes(valoreslinea[2]));
							put1.add(Bytes.toBytes("datos"),Bytes.toBytes("street"),Bytes.toBytes(valoreslinea[3]));
							put1.add(Bytes.toBytes("datos"),Bytes.toBytes("height"),Bytes.toBytes(valoreslinea[4]));
							put1.add(Bytes.toBytes("datos"),Bytes.toBytes("streetNumber"),Bytes.toBytes(valoreslinea[5]));
							put1.add(Bytes.toBytes("datos"),Bytes.toBytes("nearby"),Bytes.toBytes(valoreslinea[6]));
							put1.add(Bytes.toBytes("datos"),Bytes.toBytes("status"),Bytes.toBytes(valoreslinea[7]));
							put1.add(Bytes.toBytes("datos"),Bytes.toBytes("slots"),Bytes.toBytes(valoreslinea[8]));
							put1.add(Bytes.toBytes("datos"),Bytes.toBytes("bikes"),Bytes.toBytes(valoreslinea[9]));
							put1.add(Bytes.toBytes("datos"),Bytes.toBytes("fecha"),Bytes.toBytes(fechamvtoS));
                             
							//System.out.println("antes de put");

							tabla.put(put1);
							System.out.println("puntCount antes "+putCount);
						
							putCount = putCount + 1;
							System.out.println("puntCount despues "+putCount);
							//System.out.println("despues de put");

//							try {
//								rowCount = aggregationClient.rowCount(TABLE_NAME,null,scan);
//							} catch (Throwable e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							System.out.println(" despues rowCount "+rowCount);

						}
					}


				}
				responseBuilder.append(line + '\n');
			}


			System.out.println("antes de flush ");
			tabla.flushCommits();
			System.out.println("despues de flush");

			System.out.println("putcount "+putCount);
		    s = new Scan();
			s.addColumn(CF,Bytes.toBytes("lat"));
			scanner = tabla.getScanner(s);
			try {
			  // Scanners return Result instances.
			  // Now, for the actual iteration. One way is to use a while loop like so:
			  for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
			    // print out the row we found and the columns we were looking for
			    //System.out.println("Found row: " + rr);
				 rowCount1 = rowCount1 + 1;
			  }

			  
			  // The other approach is to use a foreach loop. Scanners are iterable!
			  // for (Result rr : scanner) {
			  //   System.out.println("Found row: " + rr);
			  // }
			} finally {
			  // Make sure you close your scanners when you are done!
			  // Thats why we have it inside a try/finally clause
			  scanner.close();
			}
			
			System.out.println("final de bucle rowCount1 "+rowCount1);


			tabla.close();
			
		     s = new Scan();
			s.addColumn(CF,Bytes.toBytes("lat"));
		    scanner = tabla.getScanner(s);
			try {
			  // Scanners return Result instances.
			  // Now, for the actual iteration. One way is to use a while loop like so:
			  for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
			    // print out the row we found and the columns we were looking for
			    //System.out.println("Found row: " + rr);
				 rowCount1 = rowCount1 + 1;
			  }

			  
			  // The other approach is to use a foreach loop. Scanners are iterable!
			  // for (Result rr : scanner) {
			  //   System.out.println("Found row: " + rr);
			  // }
			} finally {
			  // Make sure you close your scanners when you are done!
			  // Thats why we have it inside a try/finally clause
			  scanner.close();
			}
			
			System.out.println("despues de close  rowCount1 "+rowCount1);

			
//			try {
//				rowCount = aggregationClient.rowCount(TABLE_NAME,null,scan);
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(" final rowCount "+rowCount);
//			

			System.out.println("antes de cargar estadisticas ");

			bicing.pig.PigEstadisticas pg = new bicing.pig.PigEstadisticas();
			pg.cargarEstadisticas(fechamvtoL);
			System.out.println("fin de cargar Estadisticas");

		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	private void creaTabla() throws IOException {

		if (admin.tableExists(tableName)){

			 System.out.println("tabla existe");
			//	admin.disableTable(tableName);
			//   admin.deleteTable(tableName);
			HTableDescriptor desc = new HTableDescriptor(tableName);
			tabla = new HTable(conf,tableName);
		   //  configuration = HBaseConfiguration.create(conf);
			
			// Sometimes, you won't know the row you're looking for. In this case, you
			// use a Scanner. This will give you cursor-like interface to the contents
			// of the table.  To set up a Scanner, do like you did above making a Put
			// and a Get, create a Scan.  Adorn it with column names, etc.
			s = new Scan();
			s.addColumn(CF,Bytes.toBytes("lat"));
			scanner = tabla.getScanner(s);
			try {
			  // Scanners return Result instances.
			  // Now, for the actual iteration. One way is to use a while loop like so:
			  for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
			    // print out the row we found and the columns we were looking for
			    //System.out.println("Found row: " + rr);
				 rowCount1 = rowCount1 + 1;
			  }

			  
			  // The other approach is to use a foreach loop. Scanners are iterable!
			  // for (Result rr : scanner) {
			  //   System.out.println("Found row: " + rr);
			  // }
			} finally {
			  // Make sure you close your scanners when you are done!
			  // Thats why we have it inside a try/finally clause
			  scanner.close();
			}
			
			System.out.println("rowCount1 "+rowCount1);
//			aggregationClient = new AggregationClient(conf); 
//     		scan = new Scan();
//     		scan.addFamily(CF);
//			try {
//				rowCount = aggregationClient.rowCount(TABLE_NAME,null,scan);
//				System.out.println("rowCount "+rowCount);
// 		} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
		}
		else {
			System.out.println("tabla no existe");
		}

		if (!admin.tableExists(tableName)) {
           

			System.out.println("tabla no existe ");
			HTableDescriptor desc = new HTableDescriptor(tableName);

			HColumnDescriptor coldef1 = new HColumnDescriptor(columnFamility1);
			coldef1.setMaxVersions(300);

			desc.addFamily(coldef1);


			admin.createTable(desc);

			tabla = new HTable(conf,tableName);
			
//		     configuration = HBaseConfiguration.create(conf);
//			 aggregationClient = new AggregationClient(conf); 
//			scan = new Scan();
//			scan.addFamily(CF);
//			try {
//				rowCount = aggregationClient.rowCount(TABLE_NAME,null,scan);
//				System.out.println("rowCount "+rowCount);
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}


	}

	private void creaRegistro() throws IOException {

		HTable tabla = new HTable(conf,tableName);

		try {
			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {

				String[] datoslinea =  line.split(cvsSplitBy);

				String rowkey = datoslinea[0];


				Calendar c1 = Calendar.getInstance();
				String dia = Integer.toString(c1.get(Calendar.DATE));
				String mes = Integer.toString(c1.get(Calendar.MONTH));
				String annio = Integer.toString(c1.get(Calendar.YEAR));

				String hora = Integer.toString(c1.get(Calendar.HOUR_OF_DAY));
				String minute = Integer.toString(c1.get(Calendar.MINUTE));
				String second = Integer.toString(c1.get(Calendar.SECOND));
				String milisecond = Integer.toString(c1.get(Calendar.MILLISECOND));


				String date = dia+mes+annio+hora+minute+second+milisecond;

				String timestamp = date;




				Put put1 = new Put(Bytes.toBytes(rowkey),Long.valueOf(timestamp));


				put1.add(Bytes.toBytes("datos"),Bytes.toBytes("lat"),Bytes.toBytes(datoslinea[1]));
				put1.add(Bytes.toBytes("datos"),Bytes.toBytes("long"),Bytes.toBytes(datoslinea[2]));
				put1.add(Bytes.toBytes("datos"),Bytes.toBytes("street"),Bytes.toBytes(datoslinea[3]));
				put1.add(Bytes.toBytes("datos"),Bytes.toBytes("height"),Bytes.toBytes(datoslinea[4]));
				put1.add(Bytes.toBytes("datos"),Bytes.toBytes("streetNumber"),Bytes.toBytes(datoslinea[5]));
				put1.add(Bytes.toBytes("datos"),Bytes.toBytes("nearby"),Bytes.toBytes(datoslinea[6]));
				put1.add(Bytes.toBytes("datos"),Bytes.toBytes("status"),Bytes.toBytes(datoslinea[7]));
				put1.add(Bytes.toBytes("datos"),Bytes.toBytes("slots"),Bytes.toBytes(datoslinea[8]));
				put1.add(Bytes.toBytes("datos"),Bytes.toBytes("bikes"),Bytes.toBytes(datoslinea[9]));
				put1.add(Bytes.toBytes("datos"),Bytes.toBytes("fecha"),Bytes.toBytes(fechamvtoS));

				tabla.put(put1);


			}

			tabla.close();


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();

		} finally {
			if (br!=null){
				try {
					br.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}

	}

	private void consultaRegistro()  throws IOException {


		HTable tabla = new HTable(conf,tableName);

		Get get = new Get(Bytes.toBytes("1"));

		byte[] rowDevuelta = get.getRow();



		String qualifier="lat";

		get.addColumn(Bytes.toBytes(columnFamility1 ),Bytes.toBytes(qualifier));


		get.setMaxVersions(1);

		Result result = tabla.get(get);

		if (result.containsColumn(Bytes.toBytes(columnFamility1), Bytes.toBytes(qualifier))) {

			System.out.println("contiene totals lat");
		}
		else {

			System.out.println("no contiene total lat ");
		}	



		byte[] val = result.getValue(Bytes.toBytes(columnFamility1), Bytes.toBytes(qualifier));



		List<KeyValue> listaResultados = result.list();

		for (KeyValue kv : listaResultados) {

			System.out.println("KeyValue "+kv.toString());
		}
	}
	
	public static void main(String[] args )throws IOException, InterruptedException { 	

		System.out.println("empezando a generar ");
		Generadortabla gt = new Generadortabla();
		System.out.println(" antes de crear tabla ");
		gt.creaTabla();
		//while (true) {

        System.out.println("antes de cargaXML");
			gt.cargarXML();
		//}   

	}
}