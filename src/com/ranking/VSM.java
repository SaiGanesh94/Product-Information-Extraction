package com.ranking;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import comcrawler.*;

public class VSM extends ListCrawler {

	public void vsm() throws Exception{
		File f=new File("E:\\e-book\\package\\ProductNames.txt");
		
		LinkedHashSet<String> h1=new LinkedHashSet<String>();
		
		Scanner s=new Scanner(f);
		Scanner s1=new Scanner(f);
		int count=0;
		while(s.hasNextLine()){
			String input=s.nextLine();
			String[] split=input.split(" ");
			
			for(int i=0;i<split.length;i++){
				h1.add(split[i]);
			}
			count++;
		}
		System.out.println("LinkedHashSet:"+h1.size());
		System.out.println("Products:"+count);
		int[][] matrix=new int[h1.size()][count];
		s.reset();
		count=0;
		
		for(int i=0;i<h1.size();i++){
			for(int j=0;j<count;j++){
				matrix[i][j]=0;
			}
		}
		while(s1.hasNextLine()){
			HashMap<String,Integer> hash=new HashMap<String,Integer>();
			
			String str=s1.nextLine();
			String[] inputSplit=str.split(" ");
			
			
			for(int i=0;i < inputSplit.length;i++){
				if(hash.get(inputSplit[i])==null){
					hash.put(inputSplit[i], 1);
				}
				else{
					hash.put(inputSplit[i], hash.get(inputSplit[i])+1);
				}
			}
			
			Iterator itr=h1.iterator();
			int i=0;
			while(itr.hasNext()){
				String linkStr=(String)itr.next();
				
				if(hash.get(linkStr)==null){
					matrix[i][count]=0;
				}
				else{
					matrix[i][count]=hash.get(linkStr);
				}
				i++;
			}
		count++;
		}
		
		System.out.println("Count:"+count);
		File file=new File("E:\\e-book\\package\\Matrix.txt");
		for(int i=0;i<h1.size();i++){
			FileWriter fw=new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bufferWritter = new BufferedWriter(fw);
			bufferWritter.newLine();
			for(int j=0;j<count;j++){
				
				bufferWritter.write(matrix[i][j]+" ");
			}
			bufferWritter.newLine();
			bufferWritter.close();
			System.out.println();
		}
		
		calculateTermMatrix(matrix,h1,count);
		
	}

	public  void calculateTermMatrix(int[][] matrix, LinkedHashSet<String> h1, int count) throws Exception {
		
		int max=0;
		float[][] termMatrix=new float[h1.size()][count];
		
		for(int i=0;i<count;i++){
			for(int j=0;j<h1.size();j++){
				if(max<matrix[j][i]){
					max=matrix[j][i];
				}
			}
			
			// Normalized Term Document Matrix
			for(int j=0;j<h1.size();j++){
				termMatrix[j][i]=matrix[j][i]/max;
			}
		}
		
		ArrayList<Float> a1=new ArrayList<Float>();
		
		for(int i=0;i<h1.size();i++){
			float df=0;
			for(int j=0;j<count;j++){
				if(matrix[i][j]!=0){
					df++;
				}
			}
			a1.add(i,(count/df));
		}
		
		for(int i=0;i<h1.size();i++){
			for(int j=0;j<count;j++){
				termMatrix[i][j]=termMatrix[i][j]*a1.get(i);
			}
		}
		
		File file=new File("E:\\e-book\\package\\termMatrix.txt");
		
		for(int i=0;i<h1.size();i++){
			FileWriter fw=new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bufferWritter = new BufferedWriter(fw);
		
			for(int j=0;j<count;j++){
				bufferWritter.write(termMatrix[i][j]+" ");
			}
			bufferWritter.newLine();
			bufferWritter.close();
		
		}
		assignQueryMatrix(termMatrix,h1,count,a1);
	}

	public void assignQueryMatrix(float[][] termMatrix, LinkedHashSet<String> h1,
			int count,ArrayList<Float> a1) throws Exception {
		
		
		File f=new File("E:\\e-book\\package\\Query.txt");
		
		HashMap<String,Integer> h2=new HashMap<String,Integer>();
		
		Scanner s=new Scanner(f);
		
			String str=s.nextLine();
			
			String[] split=str.split(" ");
		
			for(int i=0;i<split.length;i++){
				if(h2.get(split[i])==null){
					h2.put(split[i], 1);
				}
				else {
					h2.put(split[i], h2.get(split[i])+1);
				}
			}
		
		Iterator itr=h1.iterator();
		
		float[] query=new float[h1.size()];
		int i=0;
		while(itr.hasNext()){
			String input=(String)itr.next();
			
			if(h2.get(input)==null){
				query[i]=0;
			}
			else{
				query[i]=h2.get(input);
			}
			i++;
		}
		
		for(int p=0;p<query.length;p++){
			query[p]=query[p]*(float)Math.log(count/a1.get(p));
		}
		
		File file=new File("E:\\e-book\\package\\finalMatrix.txt");
		
		for(int k=0;k<h1.size();k++){
			FileWriter fw=new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bufferWritter = new BufferedWriter(fw);
			bufferWritter.write(query[k]+" ");
			bufferWritter.newLine();
			bufferWritter.close();
		
		}
	calculateCosineSimilarity(termMatrix,query,h1,count);	
	}

	public void calculateCosineSimilarity(float[][] termMatrix, float[] query,
			LinkedHashSet<String> h1,int count) throws Exception {
		
		double[] output=new double[count];
		
		TreeMap<Double,String> tree1=new TreeMap<Double,String>();
		File f=new File("E:\\e-book\\package\\ProductNames.txt");
		Scanner s=new Scanner(f);
		for(int i=0;i<count;i++){
			float sum=0;
			double termMatrixSquare=0;
			double queryMatrixSquare=0;
			for(int j=0;j<h1.size();j++){
				
				sum=sum+termMatrix[j][i]*query[j];
				
				termMatrixSquare=termMatrixSquare+termMatrix[j][i]*termMatrix[j][i];
				queryMatrixSquare=queryMatrixSquare+query[j]*query[j];
			}
			double num=Math.sqrt(termMatrixSquare)*Math.sqrt(queryMatrixSquare);
			if(sum!=0.0 && num!=0.0){
			double cosine=sum/num;
			
			output[i]=cosine;
			
			if(s.hasNextLine())
				tree1.put( output[i],s.nextLine());
			}
			else{
				output[i]=0.0;
				
				if(s.hasNextLine())
					tree1.put( output[i],s.nextLine());
				}
			}
			
		
			File file=new File("E:\\e-book\\package\\output.txt");
		
			Set set=tree1.descendingKeySet();
			Iterator itr=set.iterator();
			int i=12;
			while(itr.hasNext() && i>=0){
				FileWriter fw=new FileWriter(file.getAbsoluteFile(),true);
				BufferedWriter bufferWritter = new BufferedWriter(fw);
				bufferWritter.write(tree1.get(itr.next())+"--------"+itr.next());
				bufferWritter.newLine();
				bufferWritter.close();
				i--;
			}
	}
	
	
}
