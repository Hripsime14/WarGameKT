open class Human(health: Int, strength: Int, speed: Int, type: String) : Soldier(health, strength, speed, type) {

    fun setWeapon(weapon: HumanWeapon) {
        super.weapon = weapon
    }


}