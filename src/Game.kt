import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    val game = Game(4, 4)
    game.start()
}

class Game(row: Int, private val column: Int) { // TODO: estex inchi enq private sarqum?
    private val humanList = arrayListOf<Human>()
    private val mysticList = arrayListOf<Mystic>()

    private lateinit var activeHuman: Human
    private lateinit var activeMystic: Mystic
    private val millis = 500
    var soldiersCount: Int = 0
    private var distance: Int = 0
    var timer: Timer? = null

    init {
        createSoldiers(row)
        soldiersCount = row
        distance = column
    }

    fun start() {
        timer = Timer()
        timer?.scheduleAtFixedRate(RemindTask(soldiersCount), 0, 500)
    }

    fun doWork() {
        val hx: Double? = activeHuman.coordinates?.getX()
        val hy: Double? = activeHuman.coordinates?.getY()
        val mx: Double?  = activeMystic.coordinates?.getX()
        val my: Double? = activeMystic.coordinates?.getY()

        if (activeMystic.coordinates!!.getX()!! - activeHuman.coordinates!!.getX()!! > 1.0) {
            if (hy != null) {
                if (hx != null) {
                    activeHuman.setCoordinates(hx + activeHuman.speed*millis / 1000, hy)
                }
                println(activeHuman.type + " coordinates are -> x = " + activeHuman.coordinates!!.getX() + ", y = " + activeHuman.coordinates!!.getY())
            }
            if (my != null) {
                if (mx != null) {
                    activeMystic.setCoordinates(mx - activeMystic.speed*millis / 1000, my)
                }
                println(activeMystic.type + " coordinates are -> x = " + activeMystic.coordinates!!.getX() + ", y = " + activeMystic.coordinates!!.getY())
            }
            println()
        }
        else {
            if (activeHuman.health < 0 || activeMystic.health < 0) return
            attack();
        }
    }

    private fun createSoldiers(soldiersCount: Int) {
        var y = 0.0
        var i = 0.0

        while (soldiersCount - i++ > 0) {
            val humanSoldier: Human = selectRandomSoldier(SoldierType.HUMAN_TYPE) as Human
            humanSoldier.setCoordinates(0.0, y)
            humanSoldier.setWeapon(selectRandomWeapon(SoldierType.HUMAN_TYPE) as HumanWeapon) //TODO() estex takic setWeapon a askhatum?
            humanList.add(humanSoldier)

            val mysticSoldier: Mystic = selectRandomSoldier(SoldierType.MYSTIC_TYPE) as Mystic
            mysticSoldier.setCoordinates(column.toDouble(), y--) //TODO: estex karam hangist column parametry ogtagorcem? te piti veragrem mi bani u da ogtagorcem
            mysticSoldier.setWeapon(selectRandomWeapon(SoldierType.MYSTIC_TYPE) as MysticWeapon)
            mysticList.add(mysticSoldier)
        }
    }

    private fun selectRandomSoldier(type: Int) : Soldier? {
        var soldier: Soldier? = null //TODO() es inch harcakan a?
        if (type == SoldierType.HUMAN_TYPE) {
            when (randomNumber(3)) {
                0-> soldier = Infantry()
                1-> soldier = Commando()
                2-> soldier = General()
            }
        }
        else if (type == SoldierType.MYSTIC_TYPE) {
            when(randomNumber(3)) {
                0 -> soldier = Troll()
                1 -> soldier = Orc()
                2 -> soldier = Elf()
            }
        }
        return soldier
    }

    private fun selectRandomWeapon(type: Int) : Weapon?{
        var weapon: Weapon? = null

        if (type == SoldierType.HUMAN_TYPE) {
            when(randomNumber(3)) {
                0-> weapon = Gun()
                1-> weapon = Rifle()
                2-> weapon = Shotgun()
            }
        }
        else if (type == SoldierType.MYSTIC_TYPE) {
            when(randomNumber(3)) {
                0 -> weapon = Knife()
                1 -> weapon = Sword()
                2 -> weapon = Axe()
            }
        }
        return weapon
    }

    private fun count20percent(soldier: Soldier): Int { //TODO:es masy miak anelu dzevn es a?
        if (soldier is Infantry) return soldier.getInitialHealth()
        if (soldier is Commando) return soldier.getInitialHealth()
        if (soldier is General) return soldier.getInitialHealth()
        if (soldier is Elf) return soldier.getInitialHealth()
        if (soldier is Orc)  soldier.getInitialHealth()
        return if (soldier is Troll) soldier.getInitialHealth()
        else 0
    }

    private fun isWeak(soldier: Soldier): Boolean {
        return soldier.health < count20percent(soldier)
    }

    private fun attack() {
        println("attack is called")
        var humanMultipliedDamage = 1.0;
        var mysticMultipliedDamage = 1.0;

        if (activeHuman.coordinates?.getX()!! < distance / 2) {
            humanMultipliedDamage = 1.2
        } else mysticMultipliedDamage = 1.2

        if (activeHuman is General) {
            if (isWeak(activeMystic)) {
                if (activeMystic.weapon != null) {
                    (activeHuman as General).addWeapon(activeMystic.weapon as MysticWeapon)
                }
            }
        }
        if (activeMystic is Troll) {
            if (isWeak(activeHuman)) {
                if (activeHuman.weapon != null) {
                    (activeMystic as Troll).addWeapon(activeHuman.weapon as HumanWeapon)
                }
            }
        }

        if ((activeMystic.getOverallDamage() * humanMultipliedDamage).toInt() < 10) {
            activeHuman.health -= 100
        }
        else  activeHuman.health -= (activeMystic.getOverallDamage() * humanMultipliedDamage).toInt()
        println(activeHuman.type + ".health after attack = " + activeHuman.health)

        if ((activeHuman.getOverallDamage() * mysticMultipliedDamage).toInt() < 10) {
            activeMystic.health -= 100
        }
        else activeMystic.health -= (activeHuman.getOverallDamage() * mysticMultipliedDamage).toInt()
        println(activeMystic.type + ".health after attack = " + activeMystic.health)

        if (activeHuman.health < 0) println("Human is dead")
        if (activeMystic.health < 0) println("Mystic is dead")

    }

    private fun randomNumber(bound: Int): Int {
        return Random().nextInt(bound)
    }

    inner class RemindTask(var soldiersCount: Int) : TimerTask() { // TODO: imasty vorn a inner class-i dimac grelu inner?

        override fun run() {
            while (!isTeamLost(humanList) && !isTeamLost(mysticList)) { //TODO: javayum chi toxnum list-@ cast anel, isk kotlinum toxnuma?
                for (i: Int in 0 until soldiersCount step 1) {
                    activeHuman = humanList[i]
                    activeMystic = mysticList[i]
                    doWork()
                }
                println("_______________________")
            }
            timer?.cancel()
        }

        private fun isTeamLost(soldiers: List<Soldier>): Boolean {

            var deadSoldiers = 0
            for (soldier: Soldier in soldiers) {
                if (soldier.health <= 0) {
                    deadSoldiers++
                    println( "health = " + soldier.health + ", deadSoldiers = " + deadSoldiers + ", soldiersCount = " + soldiersCount)
                }
            }
            val isLost: Boolean

            isLost = if (soldiers.size %2 > 0) {
                deadSoldiers >= soldiersCount / 2 + 1
            } else  {
                deadSoldiers >= soldiersCount / 2
            }
            return isLost
        }
    }
}