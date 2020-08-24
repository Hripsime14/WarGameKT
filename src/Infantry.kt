class Infantry: Human(800, 7, 3, "Infantry") {
    private var HEALTH: Int = 0

    init {
        HEALTH = this.health
    }

    fun getInitialHealth() : Int{
        return HEALTH
    }
}