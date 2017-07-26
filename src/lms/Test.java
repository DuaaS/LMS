/*
 * Created By: Duaa S
 * Creation Date: 20/Nov/2014
 * Description: Tests the media player
*/
package lms;

/*Imports*/
import com.sun.jna.Native;  
import com.sun.jna.NativeLibrary;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;  
  
public class Test {  
  
  private final EmbeddedMediaPlayerComponent mediaPlayerComponent;  
  
  public static void main(String[] args) {  
    SwingUtilities.invokeLater(new Runnable() {  
      @Override  
      public void run() {  
            new Test();  
      }  
    });  
  }  
  
  
  private Test(){  
  
    // String vlcpath=".\\lib";
    //adds the VLC player path
    NativeLibrary.addSearchPath(  
    RuntimeUtil.getLibVlcLibraryName(), "c:/program files/videolan/vlc");  
    // NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "Path to native library");
    Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);  
  
    JFrame frame = new JFrame("VLC Test");  
  
    mediaPlayerComponent = new EmbeddedMediaPlayerComponent();  
  
    frame.setContentPane(mediaPlayerComponent);  
  
    frame.setLocation(100, 100);  
    frame.setSize(1050, 600);  
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    frame.setVisible(true);  
  
    System.out.println("Running");  
    //video path
    mediaPlayerComponent.getMediaPlayer().playMedia("C:\\LMS\\2.mp4");  
  }  
}  