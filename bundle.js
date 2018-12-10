let createForm = "<div class=\"container\">\n" +
    "        <h2>Account Create</h2>\n" +
    "\n" +
    "        <form>\n" +
    "            Name:<br>\n" +
    "            <input type=\"text\" id=\"name\" name=\"name\" value=\"\">\n" +
    "            <br>\n" +
    "            Nic number:<br>\n" +
    "            <input type=\"text\" id=\"nicNumber\" name=\"nicNumber\" value=\"\">\n" +
    "            <br>\n" +
    "            Mobile Number :<br>\n" +
    "            <input type=\"text\" id=\"mobileNumber\" name=\"mobileNumber\" value=\"\">\n" +
    "            <br><br>\n" +
    "            <input type=\"button\"  value=\"Create\" onclick=\"createAccount()\">\n" +
    "        </form>\n" +
    "    </div>";

let depositForm = "<div class=\"container\">\n" +
    "        <h2>Cash Deposit</h2>\n" +
    "\n" +
    "        <form>\n" +
    "            Account Number:<br>\n" +
    "            <input type=\"text\" id=\"depositAccountNumber\" name=\"depositAccountNumber\" value=\"\">\n" +
    "            <br>\n" +
    "            Amount:<br>\n" +
    "            <input type=\"text\" id=\"depositAmount\" name=\"depositAmount\" value=\"\">\n" +
    "            <br>\n" +
    "\n" +
    "            <br><br>\n" +
    "            <input type=\"button\" value=\"Deposit\" onclick=\"deposit()\">\n" +
    "        </form>\n" +
    "    </div>";

let withdrawForm = "<div class=\"container\">\n" +
    "        <h2>Cash Withdraw</h2>\n" +
    "\n" +
    "        <form>\n" +
    "            Account Number:<br>\n" +
    "            <input type=\"text\" id=\"withdrawAccountNumber\" name=\"withdrawAccountNumber\" value=\"\">\n" +
    "            <br>\n" +
    "            Amount:<br>\n" +
    "            <input type=\"text\" id=\"withdrawAmount\" name=\"withdrawAmount\" value=\"\">\n" +
    "            <br>\n" +
    "\n" +
    "            <br><br>\n" +
    "            <input type=\"button\" value=\"Withdraw\" onclick=\"withdraw()\">\n" +
    "        </form>\n" +
    "    </div>";

let transferForm = "<div class=\"container\">\n" +
    "        <h2>Cash transfer</h2>\n" +
    "\n" +
    "        <form >\n" +
    "            From Account :<br>\n" +
    "            <input type=\"text\" id=\"fromAccountNumber\" name=\"fromAccount\" value=\"\">\n" +
    "            <br>\n" +
    "            To Account:<br>\n" +
    "            <input type=\"text\" id=\"toAccountNumber\" name=\"toAccount\" value=\"\">\n" +
    "            <br>\n" +
    "            Amount:<br>\n" +
    "            <input type=\"text\" id=\"transferAmount\" name=\"transferAmount\" value=\"\">\n" +
    "            <br>\n" +
    "\n" +
    "            <br><br>\n" +
    "            <input type=\"button\" value=\"Transfer\" onclick=\"transfer()\">\n" +
    "        </form>\n" +
    "    </div>";

let findForm = "<div class=\"container\">\n" +
    "        <h2>Find Account</h2>\n" +
    "\n" +
    "        <form >\n" +
    "             Account Number :<br>\n" +
    "            <input type=\"text\" id=\"findAccountNumber\" name=\"findAccountNumber\" value=\"\">\n" +
    "            <br>\n" +
    "\n" +
    "            <br><br>\n" +
    "            <input type=\"button\" value=\"Find\" onclick=\"findAccount()\">\n" +
    "        </form>\n" +
    "    </div>";

let findActivityForm = "<div class=\"container\">\n" +
    "        <h2>See Activity</h2>\n" +
    "\n" +
    "        <form >\n" +
    "             Account Number :<br>\n" +
    "            <input type=\"text\" id=\"findActivityAccountNumber\" name=\"findActivityAccountNumber\" value=\"\">\n" +
    "            <br>\n" +
    "\n" +
    "            <br><br>\n" +
    "            <input type=\"button\" value=\"Show Activity\" onclick=\"findActivity()\">\n" +
    "        </form>\n" +
    "    </div>";


function run() {
    var e = document.getElementById("select");
    var value = e.options[e.selectedIndex].value;

    if (value === "create") {
        document.getElementById('display').innerHTML = createForm;
        document.getElementById('response').innerHTML = "" ;
        document.getElementById("response").className = "col-md-4";

    }
    if (value === "deposit") {
        document.getElementById('display').innerHTML = depositForm;
        document.getElementById('response').innerHTML = "";
        document.getElementById("response").className = "col-md-4";
    }
    if (value === "withdraw") {
        document.getElementById('display').innerHTML = withdrawForm;
        document.getElementById('response').innerHTML = "";
        document.getElementById("response").className = "col-md-4";
    }
    if (value === "transfer") {
        document.getElementById('display').innerHTML = transferForm;
        document.getElementById('response').innerHTML = "";
        document.getElementById("response").className = "col-md-4";
    }
    if (value === "find") {
        document.getElementById('display').innerHTML = findForm;
        document.getElementById('response').innerHTML = "";
        document.getElementById("response").className = "col-md-4";
    }
    if (value === "findActivity") {
        document.getElementById('display').innerHTML = findActivityForm;
        document.getElementById('response').innerHTML = "";
        document.getElementById("response").className = "col-md-4";
    }
}

let xmlhttp = new XMLHttpRequest();

function createAccount() {
    let name;
    let nicNumber;
    let mobileNumber;
    let createDetails;
    let jsonCreateDetails;


    name = document.getElementById("name").value;
    nicNumber = document.getElementById("nicNumber").value;
    mobileNumber = document.getElementById("mobileNumber").value;

    createDetails = {name: name, nicNumber: nicNumber, mobileNumber: mobileNumber};
    jsonCreateDetails = JSON.stringify(createDetails);

    xmlhttp.open("POST", "http://localhost:8080/create");
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

    xmlhttp.send(jsonCreateDetails);
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let response = JSON.parse(this.responseText);
            console.log("create Account ", response);
            if (response.name && response.mobileNumber && response.nicNumber && response.accountNumber && response.accountBalance) {

                let displayResponse = (
                   "<b>"+ "Account created !!"+ "</b>"+
                    "<br/>"+
                    "Name: " + response.name + "<br/>" +
                    "Mobile Number: " + response.mobileNumber +
                    "<br/>" + "NIC Number: " + response.nicNumber +
                    "<br/>" + "Account Number: " + response.accountNumber +
                    "<br/>" + "Account Balance: " + response.accountBalance);

                document.getElementById("response").className = "alert alert-success col-md-4";
                document.getElementById("response").innerHTML = displayResponse;
            }
            if (response.error) {
                let err = "<b>" +"Account creation failed !!" +"</b>"+ '<br/>'+ response.error;
                document.getElementById("response").className = "alert alert-danger col-md-4";
                document.getElementById("response").innerHTML = err;
            }


        }
    };


}

function findAccount() {
    let findAccountNumber;
    let findDetails;
    let jsonFindDetails;


    findAccountNumber = document.getElementById("findAccountNumber").value;

    findDetails = {findAccountNumber: findAccountNumber};
    jsonFindDetails = JSON.stringify(findDetails);

    xmlhttp.open("POST", "http://localhost:8080/find");
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    xmlhttp.send(jsonFindDetails);
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let response = JSON.parse(this.responseText);
            console.log("find ", response);
            if (response.name && response.mobileNumber && response.nicNumber && response.accountNumber && response.accountBalance) {

                let displayResponse = (
                   "<b>"+ "Account details- "+ "</b>"+
                    "<br/>"+
                    "Name: " + response.name + "<br/>" +
                    "Mobile Number: " + response.mobileNumber +
                    "<br/>" + "NIC Number: " + response.nicNumber +
                    "<br/>" + "Account Number: " + response.accountNumber +
                    "<br/>" + "Account Balance: " + response.accountBalance);

                document.getElementById("response").className = "alert alert-success col-md-4";
                document.getElementById("response").innerHTML = displayResponse;
            }
            if (response.error) {
                let err =  "<b>"+response.error+"</b>";
                document.getElementById("response").className = "alert alert-danger col-md-4";
                document.getElementById("response").innerHTML = err;
            }


        }
    };
}

function findActivity() {
    let findActivityAccountNumber;
    let findActivityDetails;
    let jsonFindActivityDetails;


    findActivityAccountNumber = document.getElementById("findActivityAccountNumber").value;

    findActivityDetails = {findActivityAccountNumber: findActivityAccountNumber};
    jsonFindActivityDetails = JSON.stringify(findActivityDetails);

    xmlhttp.open("POST", "http://localhost:8080/findActivity");
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    xmlhttp.send(jsonFindActivityDetails);
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let response = JSON.parse(this.responseText);
            console.log("findActivity ", response);
            if (response.activity) {

                let displayResponse = (
                  "<b>" + "Showing last 10 Activities- "+ "</b>"+
                    "<br/>"+
                     response.activity);

                document.getElementById("response").className = "alert alert-success col-md-4";
                document.getElementById("response").innerHTML = displayResponse;
            }
            if (response.error) {
                let err =  "<b>"+response.error+"</b>";
                document.getElementById("response").className = "alert alert-danger col-md-4";
                document.getElementById("response").innerHTML = err;
            }


        }
    };
}

function deposit() {
    let depositAccountNumber;
    let depositAmount;
    let depositDetails;
    let jsonDepositDetails;


    depositAccountNumber = document.getElementById("depositAccountNumber").value;
    depositAmount = document.getElementById("depositAmount").value;

    depositDetails = {depositAccountNumber: depositAccountNumber, depositAmount: depositAmount};
    jsonDepositDetails = JSON.stringify(depositDetails);

    xmlhttp.open("POST", "http://localhost:8080/deposit");
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    xmlhttp.send(jsonDepositDetails);
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let response = JSON.parse(this.responseText);
            console.log("deposit ", response);
            if (response.name && response.mobileNumber && response.nicNumber && response.accountNumber && response.accountBalance) {

                let displayResponse = (
                  "<b>" + "Deposit successful !!"+ "</b>" +
                    "<br/>"+
                    "Name: " + response.name + "<br/>" +
                    "Mobile Number: " + response.mobileNumber +
                    "<br/>" + "NIC Number: " + response.nicNumber +
                    "<br/>" + "Account Number: " + response.accountNumber +
                    "<br/>" + "Account Balance: " + response.accountBalance);

                document.getElementById("response").className = "alert alert-success col-md-4";
                document.getElementById("response").innerHTML = displayResponse;
            }
            if (response.error) {
                let err =  "<b>"+"Deposit failed !!"+"</b>"+ '<br/>'+ response.error;
                document.getElementById("response").className = "alert alert-danger col-md-4";
                document.getElementById("response").innerHTML = err;
            }


        }
    };
}

function withdraw() {
    let withdrawAccountNumber;
    let withdrawAmount;
    let withdrawDetails;
    let jsonWithdrawDetails;


    withdrawAccountNumber = document.getElementById("withdrawAccountNumber").value;
    withdrawAmount = document.getElementById("withdrawAmount").value;

    withdrawDetails = {withdrawAccountNumber: withdrawAccountNumber, withdrawAmount: withdrawAmount};
    jsonWithdrawDetails = JSON.stringify(withdrawDetails);

    xmlhttp.open("POST", "http://localhost:8080/withdraw");
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    xmlhttp.send(jsonWithdrawDetails);
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let response = JSON.parse(this.responseText);
            console.log("withdraw ", response);
            if (response.name && response.mobileNumber && response.nicNumber && response.accountNumber && response.accountBalance) {

                let displayResponse = (
                  "<b>" + "withdrawal successful !!"+ "</b>" +
                    "<br/>"+
                    "Name: " + response.name + "<br/>" +
                    "Mobile Number: " + response.mobileNumber +
                    "<br/>" + "NIC Number: " + response.nicNumber +
                    "<br/>" + "Account Number: " + response.accountNumber +
                    "<br/>" + "Account Balance: " + response.accountBalance);

                document.getElementById("response").className = "alert alert-success col-md-4";
                document.getElementById("response").innerHTML = displayResponse;
            }
            if (response.error) {
                let err = "<b>" +"withdrawal failed !!" +"</b> "+ '<br/>'+ response.error;
                document.getElementById("response").className = "alert alert-danger col-md-4";
                document.getElementById("response").innerHTML = err;
            }


        }
    };


}

function transfer() {
    let fromAccountNumber;
    let toAccountNumber;
    let transferAmount;
    let transferDetails;
    let jsonTransferDetails;


    fromAccountNumber = document.getElementById("fromAccountNumber").value;
    toAccountNumber = document.getElementById("toAccountNumber").value;
    transferAmount = document.getElementById("transferAmount").value;

    transferDetails = {
        fromAccountNumber: fromAccountNumber,
        toAccountNumber: toAccountNumber,
        transferAmount: transferAmount
    };
    jsonTransferDetails = JSON.stringify(transferDetails);

    xmlhttp.open("POST", "http://localhost:8080/transfer");
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    xmlhttp.send(jsonTransferDetails);
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let response = JSON.parse(this.responseText);
            console.log("cashTransfer ", response);
            if (response.fromAccountNumber && response.toAccountNumber) {

                let displayResponse = (
                   "<b>"+ "Cash transfer successful !!"+ "</b>"+
                    "<br/>"+
                    "<br/>"+
                    "From-" +
                    "<br/>"+
                    "Name: " + response.fromAccountName + "<br/>" +
                    "Mobile Number: " + response.fromAccountMobileNumber +
                    "<br/>" + "NIC Number: " + response.fromAccountNicNumber +
                    "<br/>" + "Account Number: " + response.fromAccountNumber +
                    "<br/>" + "Account Balance: " + response.fromAccountBalance +
                    "<br/>"+
                    "<br/>"+
                    "To-" +
                    "<br/>"+
                    "Name: " + response.toAccountName + "<br/>" +
                    "Mobile Number: " + response.toAccountMobileNumber +
                    "<br/>" + "NIC Number: " + response.toAccountNicNumber +
                    "<br/>" + "Account Number: " + response.toAccountNumber +
                    "<br/>" + "Account Balance: " + response.toAccountBalance);

                document.getElementById("response").className = "alert alert-success col-md-4";
                document.getElementById("response").innerHTML = displayResponse;
            }
            if (response.error) {
                let err = "<b>" + "Cash transfer failed !!" +"</b>" + '<br/>'+ response.error;
                document.getElementById("response").className = "alert alert-danger col-md-4";
                document.getElementById("response").innerHTML = err;
            }


        }
    };

}