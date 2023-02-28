package kr.co.bsd.stockgame

class User {
    val nickname : String = ""
    var id : String = ""
    var email : String = ""
    var password : String = ""

    constructor()

    constructor(email:String, id:String, password:String) {
        this.email = email
        this.id = id
        this.password = password
    }
}