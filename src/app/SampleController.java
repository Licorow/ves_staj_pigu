package app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SampleController implements Initializable{

	 private double lastX = 0.0d;
	 private double lastY = 0.0d;
	 private double lastWidth = 0.0d;
	 private double lastHeight = 0.0d;
	 private int acilanButonSayisi = 0;
	 private int acilanResimSayisi = 0;
	 private int resimSayisi = 0;
	 private String resimYollari[];
	 private String secilenKlasor="";
	 private File list[];
	

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.close();
    }

    @FXML
    void max(MouseEvent event) {
    	Node n = (Node)event.getSource(); 
    	 
        Window w = n.getScene().getWindow(); 
   
        double currentX = w.getX(); 
        double currentY = w.getY(); 
        double currentWidth = w.getWidth(); 
        double currentHeight = w.getHeight(); 
   
        Screen screen = Screen.getPrimary(); 
        Rectangle2D bounds = screen.getVisualBounds(); 
   
         if( currentX != bounds.getMinX() && 
           currentY != bounds.getMinY() && 
           currentWidth != bounds.getWidth() && 
           currentHeight != bounds.getHeight() ) { 
   
           w.setX(bounds.getMinX()); 
           w.setY(bounds.getMinY()); 
           w.setWidth(bounds.getWidth()); 
           w.setHeight(bounds.getHeight()); 
   
           lastX = currentX;  // save old dimensions 
           lastY = currentY; 
           lastWidth = currentWidth; 
           lastHeight = currentHeight; 
   
         } else { 
   
           w.setX(lastX); 
           w.setY(lastY); 
           w.setWidth(lastWidth); 
           w.setHeight(lastHeight); 
        } 
   
        event.consume();  
    }

    @FXML
    void min(MouseEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setIconified(true);
    }
    
    @FXML
    private AnchorPane blurlu_ekran;
    
    @FXML
    private ImageView imageView_suankiResim;
    
    @FXML
    private TextField txt_box_klasor;
    
    @FXML
    private Button _btn1,_btn2,_btn3, _btn4,_btn5,_btn6,_btn7,_btn8,_btn9;
    
    @FXML
    private ListView<String> listView_resimYollari;

    private Button[] butonlar;

    @FXML
    private Label lbl_acilanResimAdi;
    

    @FXML
    private Label lbl_Secilen_rs;
    
    @FXML
    private Label lbl_Duzenlenen_rs;

    @FXML
    private Label lbl_Kalan_rs;


    private void sonrakiniGoster(String yol){
    	if(acilanResimSayisi<resimSayisi) {
    		File file = new File(resimYollari[acilanResimSayisi]);
    		//System.out.println(resimYollari[acilanResimSayisi]);
    		Image image = new Image(file.toURI().toString());
    		imageView_suankiResim.setImage(image);
    		image = null;
    		
    		lbl_acilanResimAdi.setText(list[acilanResimSayisi].getName());
    		
    		if(yol!=null && yol.length()>0) {
        		File yeniYol = new File(secilenKlasor+"\\"+yol+"\\"+list[acilanResimSayisi].getName());
        		File eskiYol = new File(resimYollari[acilanResimSayisi]);
        		File f2 = new File(secilenKlasor+"\\"+yol);
        		if(!f2.exists()) {
        			f2.mkdir();
        		}
        			
        		kopyala_tasi(eskiYol, yeniYol, 0);
        		
        		//System.out.println(yeniYol);
        		lbl_Duzenlenen_rs.setText("Düzenlenen Resim Sayısı : "+acilanResimSayisi);
        		lbl_Kalan_rs.setText("Kalan Resim Sayısı      : "+(resimSayisi-acilanResimSayisi));
    		}


    	}else {
    		imageView_suankiResim.setImage(null);
    		lbl_acilanResimAdi.setText("Se�ili Dosya Yok");
    	}
    }
    
    private void resimYollariniDiziyeAl() {
    	resimYollari = new String[resimSayisi];
    	for(int i=0; i<resimSayisi; i++) {
    		resimYollari[i] = listView_resimYollari.getItems().get(i);
    	}
    	
    	for(int i=0; i<resimSayisi; i++) {
    		//System.out.println(resimYollari[i]);
    	}
    	
    }
    
    private void kopyala_tasi(File anaDoysaKonum,File kopyaDoysaKonum, int d){
    	// d != 0 KOPYALA
    	// D == 0 TA�I
        try {

            FileInputStream fis = new FileInputStream(anaDoysaKonum);
            BufferedInputStream okuyucu = new BufferedInputStream(fis);

            FileOutputStream fos = new FileOutputStream(kopyaDoysaKonum);
            BufferedOutputStream yazici = new BufferedOutputStream(fos);

            int veri = 0; // Veri Byte olarak gelece�i i�in int yeterlidir.

            while ((veri = okuyucu.read()) != -1){ // -1 olursa bitmi�tir.

                byte girdi = (byte) veri; // byte bazl� okuma
                //System.out.println("Donusumsuz : "+veri); // Gelen veriyi konsolda g�rme
                //System.out.println("Byte : "+girdi);
                yazici.write(girdi);

            }

            yazici.flush();  // Herhangi bir aksilik olursa veriyi son anda yollamak i�in
            yazici.close();  // ��lemimiz bitince dosyay� serbest b�rak�yoruz
            okuyucu.close(); // ��lemimiz bitince dosyay� serbest b�rak�yoruz
            
            if(d==0) {
            	if(kopyaDoysaKonum.exists()) {
                	anaDoysaKonum.delete();
                	
            	}
            }
        	acilanResimSayisi++;  
        	sonrakiniGoster("");
        }catch (Exception e){
            //System.out.println("Hata Kopyalama Ba�ar�s�z");
            e.printStackTrace();
        }

    }
    
    @FXML
    void btn1(ActionEvent event) {
    	sonrakiniGoster(_btn1.getText());
    }

    @FXML
    void btn2(ActionEvent event) {
    	sonrakiniGoster(_btn2.getText());
    }

    @FXML
    void btn3(ActionEvent event) {
    	sonrakiniGoster(_btn3.getText());
    }

    @FXML
    void btn4(ActionEvent event) {
    	sonrakiniGoster(_btn4.getText());
    }

    @FXML
    void btn5(ActionEvent event) {
    	sonrakiniGoster(_btn5.getText());
    }

    @FXML
    void btn6(ActionEvent event) {
    	sonrakiniGoster(_btn6.getText());
    }

    @FXML
    void btn7(ActionEvent event) {
    	sonrakiniGoster(_btn7.getText());
    }

    @FXML
    void btn8(ActionEvent event) {
    	sonrakiniGoster(_btn8.getText());
    }

    @FXML
    void btn9(ActionEvent event) {
    	sonrakiniGoster(_btn9.getText());
    }
    
    @FXML
    void ekle(ActionEvent event) {
    	if(txt_box_klasor.getText()!=null && acilanButonSayisi<butonlar.length && txt_box_klasor.getText().length()>0) {
    		butonlar[acilanButonSayisi].setText(txt_box_klasor.getText());
    		butonlar[acilanButonSayisi].setVisible(true);
    		butonlar[acilanButonSayisi].setDisable(false);
    		acilanButonSayisi++;
    	}
    	txt_box_klasor.setText(null);
    }
    
    
    @FXML
    void klasorSec(ActionEvent event) {
    	
    	
    	try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("L�tfen Bir Klas�r Se�in");
        	File selectedDirectory = directoryChooser.showDialog(null);
        	secilenKlasor=selectedDirectory.getAbsolutePath();
		} catch (Exception e) {
			
		}
        
    	if(secilenKlasor!=null && secilenKlasor.length()>0) {
        	String resimUzantilari[] = {
        			".JPEG",
        			".JPG",
        			".PNG",
        			".GIF"
        	};
            File f= new File(secilenKlasor);
            
            list = f.listFiles();
            for(int i=0;i<list.length;i++){
              //System.out.println(list[i].getName());
            	String resim = list[i].getName();
            	if(resim.lastIndexOf(".")>0) {
            		String resimUzantisi = resim.substring(resim.lastIndexOf("."), resim.length());
            		resimUzantisi=resimUzantisi.toUpperCase();
            		
            		for(int j=0; j<resimUzantilari.length; j++) {
            			if(resimUzantisi.equals(resimUzantilari[j])) {
            				resimSayisi++;
            				listView_resimYollari.getItems().add(list[i].getAbsolutePath());
            			}
            		}
            	}
            	
            	//listView_resimYollari.getItems().add(resimYolu);
            }
            resimYollariniDiziyeAl();
            sonrakiniGoster("");
            blurlu_ekran.setVisible(false);
            lbl_Secilen_rs.setText("Seçilen Resim Sayısı    : "+resimSayisi);
            lbl_Kalan_rs.setText("Kalan Resim Sayısı      : "+resimSayisi);
    	}
    	
    	if(resimSayisi<1) {
    		temizle();
    	}
        

    }
    
    @FXML
    void secimiTemizle(ActionEvent event) {
    	temizle();
    	
    }
    void temizle() {
    	resimSayisi=0;
    	acilanResimSayisi=0;
    	blurlu_ekran.setVisible(true);
    	imageView_suankiResim.setImage(null);
		lbl_acilanResimAdi.setText("Seçili Dosya Yok");
		listView_resimYollari.getItems().clear();
		butonlariGizle();
		
		lbl_Secilen_rs.setText("Seçilen Resim Sayısı    : "+0);
		lbl_Duzenlenen_rs.setText("Düzenlenen Resim Sayısı : "+0);
		lbl_Kalan_rs.setText("Kalan Resim Sayısı      : "+0);
    }

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		butonlariGizle();

	}
	
	void butonlariGizle() {
		acilanButonSayisi=0;
		butonlar = new Button[]{
	    		_btn1,
	    		_btn2,
	    		_btn3,
	    		_btn4,
	    		_btn5,
	    		_btn6,
	    		_btn7,
	    		_btn8,
	    		_btn9
			};
			for(int i=0; i<butonlar.length; i++) {
				butonlar[i].setVisible(false);
				butonlar[i].setDisable(true);
			}
	}

	
	
	
	
	
	
	
}
