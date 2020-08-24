open class Mystic(health: Int, strength: Int, speed: Int, type: String): Soldier(health, strength, speed, type) {

    fun setWeapon(mysticWeapon: MysticWeapon) {
        super.weapon = mysticWeapon
    }
}