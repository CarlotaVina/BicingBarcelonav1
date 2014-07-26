package bicing.pig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
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

/*******************************************************************************************************
 * +
 * 
 * @author cloudera
 * 
 *         Esta clase se encarga de crear la tabla HBASE TotalBicis en la que se
 *         almacena
 * 
 ***************************************************************************************************************/

public class PigEstadisticas {

	private static Configuration conf = null;
	private static Configuration conf1 = null;
	private static Configuration conf2 = null;
	public static final String tableName = "TotalBicis";
	public static final String columnFamility1 = "total";
	public static final String columnFamility2 = "direcciones";
	public static final String columnFamility3 = "fechas";
	final static String Name = "export";
	private static HBaseAdmin admin = null;
	public static HTable tabla = null;

	/************************************************************************************************************
	 * 
	 * @param fechamvtoL
	 * @return Metodo que ejecuta una consulta pig sobre la tabla DatosBicing y
	 *         almacena los datos en otra tabla
	 * 
	 ******************************************************************************************************/
	public String cargarEstadisticas(long fechamvtoL) {
		try {
			//System.out.println("en cargar estadisiticas");
			// Properties props = new Properties();
			// props.setProperty("fs.default.name","hdfs://localhost.localdomain:8020");
			// System.out.println("cargar estadisticas paso1");
			// props.setProperty("mapred.job.tracker","localhost.localdomain:8021");
			// System.out.println("cargar estadisticas paso 11 jjjjl");
			PigServer pigServer = new PigServer("local");
			// PigServer pigServer = new PigServer("mapreduce");
			runMyQuery(pigServer, "hbase://DatosBicing");
			return new String("1");
		} catch (IOException e) {
			e.printStackTrace();
			return new String("0");
		} catch (Exception e) {
			e.printStackTrace();
			return new String("0");
		}
	}

	/****************************************************************************
	 * 
	 * @param pigServer
	 * @param inputFile
	 * @throws IOException
	 *             Metodo para ejecutar la consulta
	 ***************************************************************************/
	public static void runMyQuery(PigServer pigServer, String inputFile)
			throws IOException {

		String s;
		try {
			Class exampleClass = Class
					.forName("org.apache.pig.backend.hadoop.hbase.HBaseStorage");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat dateformat = new SimpleDateFormat("ddMMyyyyhhmmss");
		Calendar cal = Calendar.getInstance();
		String currentfecha = dateformat.format(cal.getTime());
		Long fecha = Long.valueOf(currentfecha).longValue();
		Date date1 = new Date(Long.MIN_VALUE);
		SimpleDateFormat dateformatddMMyyyy = new SimpleDateFormat(
				"ddMMyyyyhhmmss");
		StringBuilder nowddMMyyyy = new StringBuilder(
				dateformatddMMyyyy.format(date1));
		Date date2 = new Date(Long.MAX_VALUE);
		SimpleDateFormat dateformatddMMyyyy1 = new SimpleDateFormat(
				"ddMMyyyyhhmmss");
		StringBuilder nowddMMyyyy1 = new StringBuilder(
				dateformatddMMyyyy1.format(date2));

		pigServer
				.registerQuery("source = load '"
						+ inputFile
						+ "' USING  org.apache.pig.backend.hadoop.hbase.HBaseStorage( "
						+ "'datos:bikes , datos:fecha  datos:street', '-loadKey true')"
						+ "as (id1:int, bikes:int, fecha:chararray, street:chararray) ;");

		Path output = new Path("/home/cloudera/proyecto/consulta1");
		conf = new Configuration(true);
		conf.addResource(new Path(
				"/etc/hadoop/conf.cloudera.hdfs1/core-site.xml"));
		String current = System.getProperty("user.dir");
		FileSystem hdfs = FileSystem.get(conf);

		// String
		// filePathDirectory="hdfs://localhost.localdomain:8020/user/cloudera/consultaBicing1";
		// String filePathDirectory="/consultaBicing1";
		String filePathDirectory = "consultaBicing1";
		String dir2 = current + "/consultaBicing1";
		File f = new File(dir2);
		if (f.exists()) {
			delete(f);

			if (f.isDirectory()) {
				if (f.list().length > 0) {
				} else {
					f.delete();
				}
			} else {
			}
		} else {
		}
		Path path = new Path(filePathDirectory);
		pigServer.store("source", path.toString());
		String filePathDirectory1 = "consultaBicing2";
		Path path2 = new Path(filePathDirectory1);
		String directorio = "/" + filePathDirectory1;
		hdfs.mkdirs(new Path(directorio));

		File path3 = new File(dir2);

		// String source =
		// "/home/cloudera/workspace/bicinghbase/consultaBicing1/part-m-00000";
		// String target = directorio+"/part-m-00000";

		String path4 = path2 + "";
		for (File c : path3.listFiles()) {
			String nombreFichero = c.getName();
			String source1 = c.getAbsolutePath();

			if (c.exists()) {
			}
			int i = nombreFichero.trim().compareTo("part-m-0000");
			if (i == 1) {
				hdfs.copyFromLocalFile(new Path(source1), new Path(path4));
				if (hdfs.exists(new Path("consultaBicing2/part-m-00000"))) {
				}

			}
		}

		FileStatus[] dir = hdfs.listStatus(path2);
		for (FileStatus fileStatus : dir) {
			String s1 = fileStatus.getPath().getName();
		}
		hdfs.close();
		conf = new Configuration(true);
		conf.addResource(new Path(
				"/etc/hadoop/conf.cloudera.hdfs1/core-site.xml"));
		hdfs = FileSystem.get(conf);
		// String filePathString = "numerobicis1";
		String filePathString = "consultaBicing2";
		Path path1 = new Path(filePathString);
		conf1 = HBaseConfiguration.create();
		admin = new HBaseAdmin(conf1);
		if (admin.tableExists(tableName)) {
			tabla = new HTable(conf1, tableName);
		} else {
			HTableDescriptor desc = new HTableDescriptor(tableName);
			HColumnDescriptor coldef1 = new HColumnDescriptor(columnFamility1);
			HColumnDescriptor coldef2 = new HColumnDescriptor(columnFamility2);
			HColumnDescriptor coldef3 = new HColumnDescriptor(columnFamility3);
			coldef1.setMaxVersions(300);
			desc.addFamily(coldef1);
			desc.addFamily(coldef2);
			desc.addFamily(coldef3);
			admin.createTable(desc);
			tabla = new HTable(conf1, tableName);
		}
		if (hdfs.exists(path1)) {
			boolean anadir = true;
		} else {
			boolean anadir = false;
		}
		FileStatus[] dir1 = hdfs.listStatus(path1);
		int valorkey = 1;
		for (FileStatus fileStatus1 : dir1) {
			FSDataInputStream in = hdfs.open(fileStatus1.getPath());
			byte[] buffer = new byte[1024];
			in.read(buffer);
			in.seek(0);
			List<String> lines = IOUtils.readLines(in);
			for (String cadena : lines) {
				String cadena1 = cadena + "\n";
				int pos = cadena1.indexOf(" ", 1);
				int l = cadena1.length();
				int pos1 = cadena1.indexOf("\b", 1);
				String[] valores = cadena1.toString().split(" ");
				String direccion = null;
				String[] valores1 = new String[5];
				int contadorblancos = 0;
				String valor1 = "";
				String valor2 = "";
				String valor3 = "";
				String valor4 = "";
				for (int i = 0; i < cadena1.length(); i++) {
					char c = cadena1.charAt(i);
					String elemento = c + "";
					if (elemento.replaceAll("\\d+", "").length() > 0) {
					} else {
					}
					if (elemento.equals("")) {
					} else {
					}

					int i1 = elemento.compareTo(" ");

					if (i1 < 0) {

						contadorblancos = contadorblancos + 1;
					} else {
						if (contadorblancos == 0) {
							valor1 = valor1 + elemento;
						}
						if (contadorblancos == 1) {
							valor2 = valor2 + elemento;

						}
						if (contadorblancos == 2) {
							valor3 = valor3 + elemento;

						}
						if (contadorblancos == 3) {
							valor4 = valor4 + elemento;

						}
					}

				}
                
				Long valorkeyL = new Long(valorkey);
				String valorkeyS = Long.toString(valorkey);
				Put put1 = new Put(Bytes.toBytes(valorkeyS),
						Long.valueOf(valor3));
				//System.out.println("tabla "+tabla.getTableName()+" valorkeys "+valorkeyS+"valor 2"+valor2+" valor3 "+valor3);

				put1.add(Bytes.toBytes("total"), Bytes.toBytes("totalbicis"),
						Bytes.toBytes(valor2));
				put1.add(Bytes.toBytes("direcciones"),
						Bytes.toBytes("direccion"), Bytes.toBytes(valor4));
				put1.add(Bytes.toBytes("fechas"), Bytes.toBytes("fecha"),
						Bytes.toBytes(valor3));
				tabla.put(put1);
				valorkey = valorkey + 1;
			}
			tabla.close();
			in.close();
		}
	}

	static void delete(File f) throws IOException {
		// System.out.println("en delete "+f.listFiles().length);

		for (File c : f.listFiles()) {
			c.delete();

		}

	}

	public static void main(String[] args) {

		// PigEstadisticas pg = new PigEstadisticas();
		// pg.cargarEstadisticas();

	}

}