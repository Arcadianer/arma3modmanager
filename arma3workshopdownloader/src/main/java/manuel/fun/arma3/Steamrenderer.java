package manuel.fun.arma3;

import java.io.*;



public class Steamrenderer implements Runnable{

    InputStream inputStream;
    OutputStream outputStream;
    StringBuilder sb;
    boolean buffer;
    
    public Steamrenderer(InputStream in,OutputStream out,boolean buffer) {
		inputStream=in;
		outputStream=out;
		this.buffer=buffer;
	}
    
    @Override
    public void run() {
    	sb=new StringBuilder();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter printWriter=new PrintWriter(new OutputStreamWriter(outputStream));
        try {
            String output="";
            while ((output=bufferedReader.readLine())!=null){
                printWriter.write(output+"\n");
                printWriter.flush();
                sb.append(output+"\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    
    @Override
    public String toString() {
    	 	return sb.toString();
    }
}
