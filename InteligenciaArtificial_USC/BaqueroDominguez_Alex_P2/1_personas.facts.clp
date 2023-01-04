(deffacts VariosHechos (EsPadre Pedro) (EsPadre Juan))

(defrule LosPadresQuierenALosHijos (EsPadre ?variable)
=>
(assert (QuiereASusHijos ?variable)))