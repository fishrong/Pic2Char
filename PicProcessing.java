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
	//字符由简单到复杂，用来表示不同灰度
	private String str="@#&$%*o!;.";
	//桌面路径
	String desktopDir = System.getProperty("user.home")+"\\Desktop\\";
	//图片名称
	private String picName = "";
	/**
	 * 初始化，读取图片属性
	 * @throws IOException
	 * @param width 图片宽度
	 * @param height 图片高度
	 */
	public void init() throws IOException {
//		System.out.println(desktopDir+picName);
		
		width = oriImg.getWidth();
		height = oriImg.getHeight();
		BinaryImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
//		System.out.println("width:"+width+"height:"+height);

	}
	/**
	 * 转化为字符画
	 * @throws IOException
	 * @param r RGB红色分量
	 * @param g RGB绿色分量
	 * @param b RGB蓝色分量
	 * @param grey 由三原色按权值合成灰度值范围0~255
	 * @param index 从灰度值黑到白对应在字符串中的索引
	 */
	public void ToBinaryImage() throws IOException{
		int r,g,b,index;
		int grey;
		//字符画路径
		String txtDir=desktopDir+picName.split("\\.")[0]+".txt";
		//写入文本文件
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
				//生成灰色图片
//				BinaryImage.setRGB(j, i, (grey << 16) | (grey << 8) | grey);
			}
			fw.write("\r\n");
//			System.out.print("\n");
			
		}}
		//保存图片
//		ImageIO.write(BinaryImage, "jpg", new File("./src/image/3.jpg"));
		System.out.println("................................已生成字符画:),存放在桌面......................");
		//打开字符画文件
		Runtime rt = Runtime.getRuntime();
		rt.exec("cmd.exe  /c "+txtDir);
		
	}
/**
 * 选择文件路径
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
		System.out.println("没有找到相应图片，请重新输入");
		ChosePic();
	}
}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PicProcessing p = new PicProcessing();
		System.out.println(">>>>>>>>>>>>请先将图片放在桌面<<<<<<<<<<<<<<");
		while(true) {
		System.out.print(">>>请输入文件名(含后缀)：");
		p.ChosePic();
		p.init();
		p.ToBinaryImage();}
	}

}
