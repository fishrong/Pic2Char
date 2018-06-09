package cn.wangsr;

import java.awt.image.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.*;;

public class PicProcessing {
	BufferedImage oriImg;
	BufferedImage BinaryImage;
	private int width;
	private int height;
	private int rgb;
	//�ַ��ɼ򵥵����ӣ�������ʾ��ͬ�Ҷ�
	private String str="@#&$%*o!;.";
	//����·��
	String desktopDir = System.getProperty("user.home")+"\\Desktop\\";
	//ͼƬ����
	private String picName = "";
	/**
	 * ��ʼ������ȡͼƬ����
	 * @throws IOException
	 * @param width ͼƬ���
	 * @param height ͼƬ�߶�
	 */
	public void init() throws IOException {
//		System.out.println(desktopDir+picName);
		
		width = oriImg.getWidth();
		height = oriImg.getHeight();
		BinaryImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
//		System.out.println("width:"+width+"height:"+height);

	}
	/**
	 * ת��Ϊ�ַ���
	 * @throws IOException
	 * @param r RGB��ɫ����
	 * @param g RGB��ɫ����
	 * @param b RGB��ɫ����
	 * @param grey ����ԭɫ��Ȩֵ�ϳɻҶ�ֵ��Χ0~255
	 * @param index �ӻҶ�ֵ�ڵ��׶�Ӧ���ַ����е�����
	 */
	public void ToBinaryImage() throws IOException{
		int r,g,b,index;
		int grey;
		//�ַ���·��
		String txtDir=desktopDir+picName.split("\\.")[0]+".txt";
		//д���ı��ļ�
		try(FileWriter fw = new FileWriter(txtDir)){
		for(int i=0;i<height;i+=8) {
			for(int j=0;j<width;j+=5) {
				rgb = oriImg.getRGB(j, i);
				r = (rgb & 0xff0000) >>> 16;
				g = (rgb & 0xff00) >>> 8;
				b = (rgb & 0xff);
				grey =(int)(0.299*r+0.587*g+0.114*b);
				
				index = Math.round((grey*str.length())/255);
				if(index>=str.length()) {
					fw.write(" ");
				}
				else fw.write(str.charAt(index));
				
//				System.out.print(index>=str.length() ? " ":str.charAt(index));
				//���ɻ�ɫͼƬ
//				BinaryImage.setRGB(j, i, (grey << 16) | (grey << 8) | grey);
			}
			fw.write("\r\n");
//			System.out.print("\n");
			
		}}
		//����ͼƬ
//		ImageIO.write(BinaryImage, "jpg", new File("./src/image/3.jpg"));
		System.out.println("................................�������ַ���:),���������......................");
		//���ַ����ļ�
		Runtime rt = Runtime.getRuntime();
		rt.exec("cmd.exe  /c "+txtDir);
		
	}
/**
 * ѡ���ļ�·��
 * @throws IOException 
 */
public void ChosePic() {
	Scanner sc = new Scanner(System.in);
	
	if(sc.hasNextLine()) {
		picName = sc.nextLine();
	}
	
	try {
	oriImg = ImageIO.read(new File(desktopDir+picName));
	}
	catch(IOException e) {
		System.out.println("û���ҵ���ӦͼƬ������������");
		ChosePic();
	}
}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PicProcessing p = new PicProcessing();
		System.out.println(">>>>>>>>>>>>���Ƚ�ͼƬ��������<<<<<<<<<<<<<<");
		while(true) {
		System.out.print(">>>�������ļ���(����׺)��");
		p.ChosePic();
		p.init();
		p.ToBinaryImage();}
	}

}
