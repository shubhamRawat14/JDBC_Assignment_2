import java.sql.*;
import java.util.Scanner;
import java.util.Optional;


public class JDBC_Assignment2 {
    public static void AddInProductTable(Connection con){
        char ch;
        try{
            System.out.print("\n Adding in Product table\n"+"-".repeat(30));
            do{
                Scanner scanner = new Scanner(System.in);
                // to insert into the Cart Table
                System.out.print("\nEnter Product ID: ");
                int Pid = scanner.nextInt();
                System.out.print("\nEnter Price: ");
                int price = scanner.nextInt();
                System.out.print("\nEnter the name of Product: ");
                String name= scanner.next();
                PreparedStatement stm=con.prepareStatement("insert into Product values(?,?,?)");
                stm.setInt(1,Pid);//1  parameter in the query
                stm.setInt(2,price);//2  parameter in the query
                stm.setString(3,name);
                stm.executeUpdate();
                System.out.println(" item is added");

                System.out.print("\n Want to Continue y/n: ");

                ch = scanner.next().charAt(0);

            }while(ch!='n');
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Product");

            System.out.println("\t Products in DATABASE \n"+"-".repeat( 30 ) );
            while(rs.next())
                System.out.println(String.format("%"+3+"s",rs.getInt(1))+"  "+String.format("%" + 3 + "s", rs.getInt(2))+"  "+rs.getString(3));

            System.out.println("-".repeat( 30 ) );

        }
        catch (Exception e){
            System.out.println(""+e);
        }
    }

    public static void AddInCartTable(Connection con){
        char ch;
        try{
            System.out.print("\n Adding in Cart table\n"+"-".repeat(30));
            do{
                Scanner scanner = new Scanner(System.in);
                // to insert into the Cart Table
                System.out.print("\nEnter Product ID: ");
                int Pid = scanner.nextInt();
                System.out.print("\nEnter quantity: ");
                int quantity = scanner.nextInt();
                PreparedStatement stm=con.prepareStatement("insert into Cart values(?,?)");
                stm.setInt(1,Pid);//1  parameter in the query
                stm.setInt(2,quantity);//2  parameter in the query

                stm.executeUpdate();
                System.out.println(" item is added to the cart");

                System.out.print("\n Want to Continue y/n: ");

                ch = scanner.next().charAt(0);

            }while(ch!='n');
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Cart");

            System.out.println("\t Cart in DATABASE \n"+"-".repeat( 30 ) );
            while(rs.next())
                System.out.println(String.format("%"+3+"s",rs.getInt(1))+"  "+String.format("%" + 3 + "s", rs.getInt(2)));

            System.out.println("-".repeat( 30 ) );

        }
        catch (Exception e){
            System.out.println(""+e);
        }
    }
    public static Optional queryMethod(String str,Connection con)throws Exception{

        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(str);
        rs.next();
        Optional <String> op= Optional.of("Pid: " + rs.getInt(1)+"\nPrice: "+rs.getInt(2)+"\nName: "+rs.getString(3));
        if (op.isPresent()) {
            return op;
        }
        return Optional.empty();

    }
    public static void averagePrice(Connection con) throws Exception{
        PreparedStatement preparedStatement = con.prepareStatement("select AVG(price)  from Product");
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        System.out.println("Average Price: "+rs.getInt(1));
    }
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/Products?characterEncoding=latin1","sqluser","Anton123456@");
            //for adding products in product table
            AddInProductTable(con);
            //for adding quantity in cart table
            AddInCartTable(con);
            // for query
            System.out.println(queryMethod("Select * from Product where pid=1",con));
            //for average price
            averagePrice(con);
        }catch (Exception e){
            System.out.println(""+e);
        }
    }
}
