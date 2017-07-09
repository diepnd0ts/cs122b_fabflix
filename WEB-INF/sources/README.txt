Username: admin
Password: gaythony
IP Address: 52.25.32.130

Group Members: Paul An, Bryan Diep, Anthony Gomez

Two optimization techniques:
	Batch Insert:
		We used batch insert as an optimization technique  because the query doesn’t need to be reparsed. The queries are all uploaded in one transaction.
	Disable AutoCommit:
		We removed autocommit because manual control allows for faster commit logic.

Comments: XML Parsing Files are under TomcatForm/xmlfiles. You can compile the files here. The main function is under BatchInsert.java.

 
