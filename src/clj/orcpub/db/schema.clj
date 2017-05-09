(ns orcpub.db.schema)

(def user-schema
  [{:db/ident :orcpub.user/username
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident :orcpub.user/first-and-last-name
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident :orcpub.user/email
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident :orcpub.user/password
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one}])

(def entity-schema
  [{:db/ident ::e/key
    :db/valueType :db.type/keyword
    :db/cardinality :db.cardinality/one}
   {:db/ident ::e/options
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many
    :db/isComponent true}
   {:db/ident ::e/selection-key
    :db/valueType :db.type/keyword
    :db/cardinality :db.cardinality/one}
   {:db/ident ::e/vec-selection
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many
    :db/isComponent true}
   {:db/ident ::e/map-selection
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/isComponent true}
   {:db/ident ::e/map-value
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/isComponent true}
   {:db/ident ::char5e/str
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one}
   {:db/ident ::char5e/dex
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one}
   {:db/ident ::char5e/con
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one}
   {:db/ident ::char5e/int
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one}
   {:db/ident ::char5e/wis
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one}
   {:db/ident ::char5e/cha
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one}])
