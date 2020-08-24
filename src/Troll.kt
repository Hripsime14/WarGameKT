class Troll: Mystic(2000, 30, 2, "Troll") {
    private var HEALTH: Int = 0
    private val weapons = arrayListOf<Weapon>()

    init {
        HEALTH = this.health
    }

    fun getInitialHealth() : Int{
        return HEALTH
    }

    fun addWeapon(weapon: HumanWeapon) {
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