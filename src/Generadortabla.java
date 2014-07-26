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

/****************************************************************************************************
 * 
 * @author cloudera
 * 
 * 
 *         Esta clase recupera la informacion de la url donde estan los datos en
 *         formato XML. Una vez recuperada esta información la parsea y la
 *         almacena en una tabla HBASE. A partir de esta tabla se crea otra
 *         donde se selecciona informacion que despues se manipulara para
 *         cumplir los requisitos del proyecto
 */

public class Generadortabla {

	StringBuilder responseBuilder = new StringBuilder();
	public static final String tableName = "DatosBicing";
	private static final byte[] TABLE_NAME = Bytes.toBytes(tableName);
	public static final String columnFamility1 = "datos";
	private static final byte[] CF = Bytes.toBytes(columnFamility1);
	final static String Name = "export";
	public static final String pathTabla = "/Bicing1";
	public static final String nuevoNombre = "/datosBicing";
	public static final String viejoNombre = "/Bicing1";
	// public static final String pathTabla =
	// "hdfs://localhost:8020/user/cloudera/Bicing1";
	// public static final String nuevoNombre =
	// "hdfs://localhost:8020/user/cloudera/datosBicing";
	// public static final String viejoNombre =
	// "hdfs://localhost:8020/user/cloudera/Bicing1/part-m-00000";
	// public static final String viejoNombre =
	// "hdfs://localhost:8020/user/cloudera/Bicing1";
	// public static final String viejoNombre =
	// "hdfs://localhost.localdomain:8020/user/cloudera/Bicing1";

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

	/******************************************************************************************************
	 * 
	 * @throws MasterNotRunningException
	 * @throws ZooKeeperConnectionException
	 *             Constructor de la clase .- Se crea y se inicializa la
	 *             configuraciónde HBASE
	 */
	private Generadortabla() throws MasterNotRunningException,
			ZooKeeperConnectionException {

		this.conf = HBaseConfiguration.create();
		this.admin = new HBaseAdmin(conf);

	}

	/*******************************************************************************************
	 * Este metodo recupera la informacion XML de la URL y la parsea para
	 * almacenarla en la tabla HBASE
	 */
	private void cargarXML() {

		try {
			URL url = new URL(
					"http://wservice.viabicing.cat/getstations.php?v=1");
			URLConnection conn = url.openConnection();
			HttpURLConnection httpConn;
			httpConn = (HttpURLConnection) conn;
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream()));
			String line;
			int contador = 1;
			String[] valoreslinea = new String[10];
			Integer contadoretiquetas = 0;
			boolean dividir = false;
			while ((line = rd.readLine()) != null) {
				{
					dividir = false;
					contador = contador + 1;
					if (line.contains("updatetime")) {
						line = line.replace("CDATA", "");
						line = line.replace("!", "");
						line = line.replace("]]", "");
						line = line.replace("[[", "");
						String[] lineaupdatetime = line.split("><");
						updateTimeL = new Long(lineaupdatetime[1]);
						updateTimeL = updateTimeL * 1000;
						fechamvto = new Date(updateTimeL);
						SimpleDateFormat dateformatDDMMYYYY = new SimpleDateFormat(
								"ddMMyyyyHHmmss");

						StringBuilder nowDDMMYYYY = new StringBuilder(
								dateformatDDMMYYYY.format(fechamvto));
						fechamvtoS = nowDDMMYYYY.toString();
						fechamvtoL = Long.valueOf(fechamvtoS);
						//System.out.println("fecha de la carga "+fechamvtoS);
						fechamvtoL1 = fechamvtoL;
					}
					if (line.contains("street")) {
						line = line.replace("CDATA", "");
						line = line.replace("![[", "");
						line = line.replace("]]", "");
						dividir = true;
					}
					String[] datoslinea = line.split(">");
					if (datoslinea.length == 2) {
						dividir = true;
					}

					if (dividir) {
						String[] datoselemento2 = datoslinea[0].split("<");
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
						if (contadoretiquetas == 10) {
							contadoretiquetas = 0;
							String rowkey = valoreslinea[0];
							Put put1 = new Put(Bytes.toBytes(rowkey),
									fechamvtoL);
							put1.add(Bytes.toBytes("datos"),
									Bytes.toBytes("lat"),
									Bytes.toBytes(valoreslinea[1]));
							put1.add(Bytes.toBytes("datos"),
									Bytes.toBytes("long"),
									Bytes.toBytes(valoreslinea[2]));
							put1.add(Bytes.toBytes("datos"),
									Bytes.toBytes("street"),
									Bytes.toBytes(valoreslinea[3]));
							put1.add(Bytes.toBytes("datos"),
									Bytes.toBytes("height"),
									Bytes.toBytes(valoreslinea[4]));
							put1.add(Bytes.toBytes("datos"),
									Bytes.toBytes("streetNumber"),
									Bytes.toBytes(valoreslinea[5]));
							put1.add(Bytes.toBytes("datos"),
									Bytes.toBytes("nearby"),
									Bytes.toBytes(valoreslinea[6]));
							put1.add(Bytes.toBytes("datos"),
									Bytes.toBytes("status"),
									Bytes.toBytes(valoreslinea[7]));
							put1.add(Bytes.toBytes("datos"),
									Bytes.toBytes("slots"),
									Bytes.toBytes(valoreslinea[8]));
							put1.add(Bytes.toBytes("datos"),
									Bytes.toBytes("bikes"),
									Bytes.toBytes(valoreslinea[9]));
							put1.add(Bytes.toBytes("datos"),
									Bytes.toBytes("fecha"),
									Bytes.toBytes(fechamvtoS));
							tabla.put(put1);
						}
					}
				}
				responseBuilder.append(line + '\n');
			}

			tabla.flushCommits();
			tabla.close();

			bicing.pig.PigEstadisticas pg = new bicing.pig.PigEstadisticas();
			pg.cargarEstadisticas(fechamvtoL);
			System.out.println("final");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/***********************************************************************************************
	 * 
	 * @throws IOException
	 *             Comprobamos si la tabla HBASE existe. Si no existe se crea.
	 *             Si existe se añaden los nuevos datos a los ya existentes
	 */

	private void creaTabla() throws IOException {

		if (admin.tableExists(tableName)) {
			HTableDescriptor desc = new HTableDescriptor(tableName);
			tabla = new HTable(conf, tableName);
		} else {
		}
		if (!admin.tableExists(tableName)) {
			HTableDescriptor desc = new HTableDescriptor(tableName);
			HColumnDescriptor coldef1 = new HColumnDescriptor(columnFamility1);
			coldef1.setMaxVersions(300);
			desc.addFamily(coldef1);
			admin.createTable(desc);
			tabla = new HTable(conf, tableName);
		}
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {

		Generadortabla gt = new Generadortabla();
		gt.creaTabla();
		// while (true) {
		gt.cargarXML();
		// }
	}
}