package ownClasses.domain.domainControllers.Persistance;

import java.io.*;

/**
 * Created by iansangines on 25/05/2016.
 */
public class BinaryPersistance<E> {

    protected File tmp;

    public BinaryPersistance(){
        String filepath ="/src/data/tmp/";
        String absolutePath= new File("").getAbsolutePath();
        tmp = new File(absolutePath.concat(filepath));
        if(!tmp.mkdirs()){
            if(!tmp.exists()) System.err.println("No s'ha pogut crear el directori");
        }
    }

    public boolean write(E element, String fileName){
        File file = new File(tmp.getPath() + fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(element);
            oos.close();
            bos.close();
            fos.close();
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
        finally{return false;}
    }

    public E read(String fileName){
        File file = new File(tmp.getAbsoluteFile()+ fileName);
        E element = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            element = (E) ois.readObject();
            return element;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

        finally{
            return null;
        }
    }
}
