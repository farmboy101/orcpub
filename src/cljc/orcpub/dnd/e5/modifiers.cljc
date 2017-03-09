(ns orcpub.dnd.e5.modifiers
  (:require [clojure.spec :as spec]
            [orcpub.modifiers :as mods]
            [orcpub.entity-spec :as es]
            [orcpub.dnd.e5.character :as char5e])
  #?(:cljs (:require-macros [orcpub.entity-spec :as es]
                            [orcpub.modifiers :as mods])))

(defn subclass [cls-key nm]
  (mods/modifier ?levels (assoc-in ?levels [cls-key :subclass] nm)))

(defn race [nm]
  (mods/modifier ?race nm))

(defn background [nm]
  (mods/modifier ?background nm))

(defn subrace [nm]
  (mods/modifier ?subrace nm))

(defn resistance [value]
  (mods/set-mod ?resistances value))

(defn immunity [value]
  (mods/set-mod ?immunities value))

(defn condition-immunity [value]
  (mods/set-mod ?condition-immunities value))

(defn darkvision [value]
  (mods/modifier ?darkvision value))

(defn speed [value]
  (mods/cum-sum-mod ?speed value "Speed" (mods/bonus-str value)))

(defn swimming-speed [value]
  (mods/cum-sum-mod ?swimming-speed value "Swim Speed" (mods/bonus-str value)))

(defn climbing-speed [value]
  (mods/cum-sum-mod ?climbing-speed value "Climb Speed" (mods/bonus-str value)))

(defn unarmored-speed-bonus [value]
  (mods/cum-sum-mod ?unarmored-speed-bonus value "Unarmored Speed" (mods/bonus-str value)))

(defn ability [ability bonus]
  (mods/modifier ?abilities
                 (update ?abilities ability + bonus)
                 (clojure.string/upper-case (name ability))
                 (mods/bonus-str bonus)))

(defn abilities [abilities]
  (mods/modifier ?abilities abilities))

(defn deferred-abilities []
  (mods/deferred-modifier
    (fn [abilities]
      (es/modifier ?abilities abilities))
    {:str 12 :dex 12 :con 12 :int 12 :wis 12 :cha 12}))

(defn saving-throws [& abilities]
  (mods/modifier ?saving-throws (apply conj (or ?saving-throws #{}) abilities)))

(defn saving-throw-type-advantage [type-nm type-kw]
  (mods/vec-mod ?saving-throw-type-advantage {:name type-nm
                                              :key type-kw}))

(defn initiative [bonus]
  (mods/cum-sum-mod ?initiative bonus "Initiative" (mods/bonus-str bonus)))

(defn level [class-key class-nm level]
  (mods/modifier ?levels (update ?levels class-key merge {:class-name class-nm
                                                        :class-level level})))

(defn spell-slots [level num]
  (mods/map-mod ?spell-slots level num))

(defn spells-known [level spell-key spellcasting-ability class & [min-level qualifier]]
  (mods/modifier
   ?spells-known
   (if (>= ?total-levels (or min-level 0))
     (update
      ?spells-known
      level
      (fn [spells]
        (conj (or spells #{})
              {:key spell-key
               :ability spellcasting-ability
               :qualifier qualifier
               :class class})))
     ?spells-known)))

(defn trait [name & [description level]]
  (mods/modifier ?traits
                 (if (or (nil? level) (>= ?total-levels level))
                   (conj
                    ?traits
                    (cond-> {:name name}
                      description (assoc :description description)))
                   ?traits)))

(defn proficiency-bonus [bonus]
  (mods/modifier ?proficiency-bonus bonus))

(defn skill-proficiency [skill-kw]
  (mods/set-mod ?skill-profs skill-kw))

(defn max-hit-points [bonus]
  (mods/cum-sum-mod ?hit-point-level-increases bonus "HP" (mods/bonus-str bonus)))

(defn deferred-max-hit-points []
  (mods/deferred-mod
    "HP"
    (fn [v] (es/cum-sum-mod ?hit-point-level-increases v))
    1
    mods/bonus-str))

(defn skill-expertise [key]
  (mods/set-mod ?skill-expertise key))

(defn tool-proficiency [name key]
  (mods/set-mod ?tool-profs {:name name
                             :key key}))

(defn language [name key]
  (mods/set-mod ?languages {:name name
                            :key key}))

(defn weapon-proficiency [name key]
  (mods/set-mod ?weapon-profs {:name name
                               :key key}))

(defn armor-proficiency [name key]
  (mods/set-mod ?armor-profs {:name name
                              :key key}))

(defn light-armor-proficiency []
  (armor-proficiency "light" :light))

(defn medium-armor-proficiency []
  (armor-proficiency "medium" :medium))

(defn heavy-armor-proficiency []
  (armor-proficiency "heavy" :heavy))

(defn shield-armor-proficiency []
  (armor-proficiency "shields" :shields))

(defn action [name & [desc]]
  (mods/vec-mod ?actions {:name name
                          :description desc}))

(defn passive-perception [bonus]
  (mods/cum-sum-mod ?passive-perception bonus))

(defn passive-investigation [bonus]
  (mods/cum-sum-mod ?passive-investigation bonus))

(defn size [size]
  (mods/modifier ?size size))

(defn weapon [weapon-kw num]
  (mods/map-mod ?weapons weapon-kw num))

(defn deferred-weapon [weapon-kw]
  (mods/deferred-modifier
    (fn [num] (es/map-mod ?weapons weapon-kw num))
    1))

(defn armor [armor-kw num]
  (mods/map-mod ?armor armor-kw num))

(defn deferred-armor [armor-kw]
  (mods/deferred-modifier
    (fn [num] (es/map-mod ?armor armor-kw num))
    1))

(defn equipment [equipment-kw num]
  (mods/map-mod ?equipment equipment-kw num))

(defn deferred-equipment [equipment-kw]
  (mods/deferred-modifier
    (fn [num] (es/map-mod ?equipment equipment-kw num))
    1))

(defn extra-attack []
  (mods/cum-sum-mod ?num-attacks 1))

(defn ranged-attack-bonus [bonus]
  (mods/cum-sum-mod ?ranged-attack-bonus bonus))

(defn armored-ac-bonus [bonus]
  (mods/modifier ?armor-class-with-armor (fn [armor]
                                           (+ bonus (?armor-class-with-armor armor)))))

(defn critical [roll-value]
  (mods/set-mod ?critical roll-value))
