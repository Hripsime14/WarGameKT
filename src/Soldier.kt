open class Soldier (var health:Int, var strength: Int, var speed:Int, var type: String) {

    var Race:String = ""
    var name: String = ""
    var coordinates: Coordinates? = null
    var weapon: Weapon? = null

    open fun getOverallDamage(): Int { //TODO: inchi chi toxnum vor miat drsum stugen ete weaponn u damage @ null chen, aranc assert-i return anem normal
        if (weapon != null && weapon!!.DAMAGE != null) {
            return strength + weapon!!.DAMAGE!!
        }
        else return strength;

    }

    fun setCoordinates(x: Double, y: Double) {
        coordinates = Coordinates(x, y)
    }


}