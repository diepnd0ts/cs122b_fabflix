<style>
.dropdown {
    position: relative;
    display: inline-block;
}
.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f6f6f6;
    width: 300px;
    overflow: auto;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content p {
    color: black;
    text-decoration: none;
    display: block;
    cursor: pointer;
}

.selection p:hover {background-color: #ddd}

.show {display:block;}
.hide {display:none;}
</style>

<script language="javascript" type="text/javascript">

function completeSearch(title) {
	document.myForm.search.value = title;
	document.getElementById("myDropdown").style.display = "none";
}

function ajaxFunction(){
        var ajaxRequest;
        try {
                ajaxRequest = new XMLHttpRequest();
        } catch(e) {
                try {
                        ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                        try {
                                ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
                        } catch(e) {
                                alert("Your browser broke!");
                                return false;
                        }
                }
        }
        ajaxRequest.onreadystatechange = function(){
		//When the servlet executes a query and responds back with results
                if (ajaxRequest.readyState == 4){
			var result = (ajaxRequest.responseText).split(" | ");
			var options = "";
			if (result.length > 1) {
				//Adding options into the datalist with the queried movies
				for (var i = 0; i < result.length; i++) {
					if (result[i].includes("\'")){
						var temp;
						temp = result[i].replace("\'", "\\\'");
						options += "<p onclick=\"completeSearch('" + temp + "')\">" + result[i] + "</p>";
					}
					else{
						options += "<p onclick=\"completeSearch('" + result[i] + "')\">" + result[i] + "</p>";
					}
				}
				document.getElementById("myDropdown").style.display = "block";
				document.getElementById("movies").innerHTML = options;
			}
			else {
				document.getElementById("myDropdown").style.display = "none";
			}
                }
        }
	//Retrieves user input in search bar and sends it to the server
        var userSearch = "?search=" + document.getElementById("input").value;
        ajaxRequest.open("GET","/Fabflix/servlet/AutocompleteSearch" + userSearch, true);
        ajaxRequest.send(null);
}

</script>

<FORM name="myForm" action="/Fabflix/servlet/SearchResult" method="get">
	<div class="dropdown">
        <input type="text" name="search" id="input" class="form-control" style="width:300px;" maxlength="100" autocomplete="off" placeholder="Quick Search..." onkeyup="ajaxFunction()">
	<div id="myDropdown" class="dropdown-content">
		<p id="movies" class="selection"></p>
	</div>
	</div>
</FORM>
