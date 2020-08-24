class General: Human(1000, 10, 2,"General") {
    private var HEALTH: Int = 0
    private val weapons = arrayListOf<Weapon>()

    init {
        HEALTH = this.health
    }

    fun getInitialHealth() : Int{
        return HEALTH
    }

    fun addWeapon(weapon: MysticWeapon) {
        weapons.add(weapon)
    }

    override fun getOverallDamage() : Int{
        var damage = 0
        for (w: Weapon in weapons) {
            if (w != null) {
                if (w.DAMAGE != null) {
                    damage += w.DAMAGE
                }
            }
        }
        return strength + damage
    }
}