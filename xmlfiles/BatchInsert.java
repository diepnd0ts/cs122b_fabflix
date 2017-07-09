
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/*

The SQL command to create the table ft.

DROP TABLE IF EXISTS ft;
CREATE TABLE ft (
    entryID INT AUTO_INCREMENT,
    entry text,
    PRIMARY KEY (entryID),
    FULLTEXT (entry)) ENGINE=MyISAM;

*/

/*

Note: Please change the username, password and the name of the datbase.

*/


public class BatchInsert {

    public static void main (String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

    	
        Connection conn = null;

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String jdbcURL="jdbc:mysql://localhost:3306/moviedb";

        try {
            conn = DriverManager.getConnection(jdbcURL,"testuser", "testpass");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement psInsertRecord=null;
        String sqlInsertRecord=null;

        int[] iNoRows=null;
        
      //start parser
        List<String> sqlQueriesToRun;
    	parser dpe = new parser();
    	String f_name = "";
    	String l_name = "";
    	
    	dpe.runExample();
		dpe.movieCastList = dpe.combine(dpe.movieList, dpe.castList);
		
		sqlQueriesToRun = new ArrayList<String>();
		
		//loop through moviecastlist and split into fname lastname
		for (int i=0; i < dpe.movieCastList.size(); i++){
			//check stagename and split if necessary
			if (dpe.movieCastList.get(i).getStagename().size() >=1){
			
				for (int j = 0; j < dpe.movieCastList.get(i).getStagename().size(); j++){
					String[] full_name = dpe.movieCastList.get(i).getStagename().get(j).split(" ");
					if (full_name.length >=2){
						f_name = full_name[0];
						l_name = full_name[1];
					}
					else{
						l_name = full_name[0];
					}
					System.out.println(f_name + " " + l_name );
					
					//title year director fn ln genre
					sqlQueriesToRun.add("CALL add_movie(" + "'" +dpe.movieCastList.get(j).getTitle() +
							"', '"+ dpe.movieCastList.get(j).getYear() +
							"', '"+ dpe.movieCastList.get(j).getDirector() + "' , '' , ''" +
							", '"+ f_name + "', '" + l_name + 
							"', '"+ dpe.movieCastList.get(j).getGenre() +
							"', "+ "@movies, @stars, @genres)");
				}
			}
		}
		
		//string
//        sqlInsertRecord="CALL add_movie(?,?,?,'','',?,?,?,@movies, @stars, @genres)";
        try {
			conn.setAutoCommit(false);
			
			//insert queries
	        for (int k = 0; k < sqlQueriesToRun.size(); k++){
	        	String sqlQueryToRun = sqlQueriesToRun.get(k);
	        	psInsertRecord=conn.prepareStatement(sqlQueryToRun);
	        	psInsertRecord.addBatch();
	        	
	        }
	        //pstatement
//            psInsertRecord=conn.prepareStatement(sqlQueryToRun);

            
//            for(int i=1;i<=50;i++)
//            {
//                psInsertRecord.setInt(1, i);
//                psInsertRecord.setString(2,"My next text piece " + (i*i));
//                psInsertRecord.addBatch();
//            }

			iNoRows=psInsertRecord.executeBatch();
			conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(psInsertRecord!=null) psInsertRecord.close();
            if(conn!=null) conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}



