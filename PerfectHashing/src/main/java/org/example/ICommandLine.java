package org.example;

import java.util.Scanner;

public class ICommandLine {
    private String getKey(int list , int input){
        String result="";
        if(list==1) {
            switch (input) {
                case 1:
                    result = "exit";
                    break;
                case 2:
                    result = "square";
                    break;
                case 3:
                    result = "linear";
                    break;
                default:
                    break;

            }

        }
        return result;

    }

 public void run() {
     boolean exit = false;
     Scanner scanner = new Scanner(System.in);

     do{
         System.out.println("1 - Enter exit if you want to exit .");
         System.out.println("2 - Enter (square) for Square Hash Table .");
         System.out.println("3 - Enter (linear) for Linear Hash Table .");
         System.out.print("Enter Command :  ");
         int inputInt=0;
         try{
             inputInt = Integer.parseInt(scanner.nextLine());
         }
         catch(Exception e){
             System.out.println("ENTER VALID INTEGER !!");

         }

          String input=this.getKey(1,inputInt);

      if(input.equalsIgnoreCase("exit")){
          exit = true ;
      }
      else if(input.equalsIgnoreCase(("square"))|| input.equalsIgnoreCase("linear")){
          EnglishDictionary englishDictionary=new EnglishDictionary(input);
          boolean innerExit=false;
          System.out.println("1 - Enter back if you want to exit or choose another method .");
          System.out.println("2 - Enter (insert) then Key for insertion .");
          System.out.println("3 - Enter (delete) then Key for delete .");
          System.out.println("4 - Enter (search) then Key for searching .");
          System.out.println("5 - Enter (batchDelete) then FILE PATH to delete batch keys .");
          System.out.println("6 - Enter (batchInsert) then FILE PATH to insert batch keys .");
          do{
              System.out.print("Enter Command :  ");

              String innerIn = scanner.nextLine();
              String[] parts = innerIn.split(" ");
              String innerInput="",key="";
              if(parts.length>=2){
                   innerInput=parts[0];
                   key=parts[1];

                  if(innerInput.equalsIgnoreCase("insert")) {


                      if(englishDictionary.insert(key)){
                          System.out.println(key+" , Inserted Successfully " );
                      }
                      else{
                          System.out.println(key+", Already exist ! " );
                      }

                  }
                  else if(innerInput.equalsIgnoreCase("delete")){

                      if(englishDictionary.delete(key)){
                          System.out.println(key+" , Deleted Successfully. " );
                      }
                      else{
                          System.out.println(key + " NOT FOUND ! " );
                      }

                  }
                  else if(innerInput.equalsIgnoreCase("search")){

                      if(englishDictionary.search(key)){
                          System.out.println(key+" , Key Found. " );
                      }
                      else{
                          System.out.println(key + " NOT FOUND ! " );
                      }

                  }
                  else if(innerInput.equalsIgnoreCase("batchinsert")){

                      englishDictionary.insertBatch(key);


                  }
                  else if(innerInput.equalsIgnoreCase("batchdelete")){

                      englishDictionary.deleteBatch(key);


                  }
              }
              else if(parts.length==1&&parts[0].equalsIgnoreCase("back")){

                  innerExit=true;
              }


              else{
                  System.out.println("Unknown Key !" );
              }


          }while(!innerExit);

      }

      else {
          System.out.println("Unknown command ! " );
      }



     }while(!exit);


}
public static void main(String[] args){
        ICommandLine iCommandLine=new ICommandLine();
        iCommandLine.run();

}
}
