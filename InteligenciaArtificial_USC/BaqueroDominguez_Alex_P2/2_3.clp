;----------------------------------------------------------
; 2.3

(deffacts VariosHechos

  (IsParent Felipe Carlos)
  (IsParent Felipe Ana)
  (IsParent Felipe Andrés)
  (IsParent Felipe Eduardo)

  (IsParent Isabel Carlos)
  (IsParent Isabel Ana)
  (IsParent Isabel Andrés)
  (IsParent Isabel Eduardo)

  (IsParent Carlos Guillermo)
  (IsParent Guillermo Jorge)
  (IsParent Guillermo Carlota)
  (IsParent Guillermo Luis)

  (IsParent Carlos Enrique)
  (IsParent Enrique Archie)
  (IsParent Enrique Lilibet)

  (IsGirl Isabel)
  (IsGirl Ana)
  (IsGirl Carlota)
  (IsGirl Lilibet)
  (IsBoy Felipe) 
  (IsBoy Carlos)
  (IsBoy Andrés)
  (IsBoy Eduardo)
  (IsBoy Guillermo)
  (IsBoy Jorge)
  (IsBoy Luis)
  (IsBoy Enrique)
  (IsBoy Archie)
)	

;--------------------------------

(defrule regla-irmans
  (IsParent ?pai ?fillo)
  (IsParent ?pai ?fillo2)
  (test (neq ?fillo ?fillo2))
=>
  (assert (IsBrothers ?fillo ?fillo2))
)

(defrule regla-tios
  (IsParent ?pai ?fillo)
  (IsBrothers ?pai ?tio)
=>
  (assert (IsUncle ?tio ?fillo))
)

;sabendo as parellas que hai, diferentes tios
(defrule regla-tios2
  (IsParent ?pai ?fillo)
  (IsBrothers ?pai ?parella)
  (IsCouple ?parella ?tio)
=>
  (assert (IsUncle ?tio ?fillo))
)

(defrule regla-sobriños
  (IsUncle ?tio ?sobriño)
=>
  (assert (IsNephews ?sobriño ?tio))
)

(defrule regla-primos
  (IsUncle ?tio ?sobriño)
  (IsParent ?tio ?fillo)
=>
  (assert (IsCousin ?sobriño ?fillo))
)

(defrule regla-avos
  (IsParent ?avos ?pai)
  (IsParent ?pai ?fillo)
=>
  (assert (IsGrandParent ?avos ?fillo))
)

;-----------------------------
(defrule regla-opcion
=>
(printout t " ---- OPCIÓNS PARA IMPRIMIR ----
a) ¿Quiénes son los hermanos, tíos, sobrinos, primos y abuelos de...?
b) ¿Quiénes son tíos y de quién?
c) ¿Quiénes son abuelos y de quién?
Introduce a opcion: " ) (assert (opcion (read)))
)

;--------------------------------------------------------------------
; ¿Quiénes son los hermanos, tíos, sobrinos, primos y abuelos de ....?

(defrule regla-familia (opcion a)
=>
(printout t "Introduce o nome da persoa: ") (assert (p(read)))
)

(defrule print-tios
  (opcion a)
  (p ?persoa)
  (IsNephews ?persoa ?tio)
  =>
  (printout t ?persoa" é sobriñx de "?tio crlf)
)

(defrule print-sobrinos
  (opcion a)
  (p ?persoa)
  (IsUncle ?persoa ?sobriño)
  =>
  (printout t ?persoa" é tix de "?sobriño crlf)
)

(defrule print-primos
  (opcion a)
  (p ?persoa)
  (IsCousin ?persoa ?primo)
  =>
  (printout t ?persoa" e "?primo " son primxs " crlf)
)

(defrule print-avos
  (opcion a)
  (p ?persoa)
  (IsGrandParent ?avos ?persoa)
  =>
  (printout t ?persoa" é netx de "?avos crlf)
)

(defrule print-irmans
  (opcion a)
  (p ?persoa)
  (IsBrothers ?persoa ?irman)
  =>
  (printout t ?persoa" y "?irman " son irmáns " crlf)
)



;----------------------------------------------------------
;¿Quiénes son abuelos y de quién?

(defrule printTodosAvo
   (opcion c)
  (IsGrandParent ?avos ?neto)
  (IsBoy ?avos)
=>
  (printout t ?avos " é avó de "?neto crlf)
)

(defrule printTodasAvoa
  (opcion c)
  (IsGrandParent ?avos ?neto)
  (IsGirl ?avos)
=>
  (printout t ?avos" é avoa de "?neto crlf)
)

;---------------------------------------------------
;¿Quiénes son tíos y de quién?

(defrule printTios
  (opcion b)
  (IsUncle ?tio ?sobriño)
  (IsBoy ?tio)
=>
  (printout t ?tio" é tio de "?sobriño crlf)
)

(defrule imprimirTias
  (opcion b)
  (IsUncle ?tia ?sobriña)
  (IsGirl ?tia)
=>
  (printout t ?tia" é tia de "?sobriña crlf)
)
