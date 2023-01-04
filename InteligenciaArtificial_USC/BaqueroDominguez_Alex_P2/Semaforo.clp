(defrule regla-tipo-agente
=>
(printout t "Que tipo de agente es (vehículo/peaton)? ") (assert (tipo-agente
(read)))
)

(defrule regla-semaforo (tipo-agente ?tipo)
=>
(printout t "Que estado de sémaforo é para o " ?tipo " (verde,rojo,ambar/intermitente)? ") (assert (color-semaforo (read)))
)

(defrule agente-puede-pasar (tipo-agente ?tipo) (color-semaforo verde)
=>
(printout t "El agente " ?tipo " puede pasar" crlf)
)

(defrule agente-debe-esperar (tipo-agente ?tipo) (color-semaforo rojo)
=>
(printout t "El agente " ?tipo " debe esperar" crlf)
)

(defrule agente-con-cuidado (tipo-agente ?tipo) (color-semaforo ambar | intermitente)
=>
(printout t "El agente " ?tipo " debe pasar con cuidado" crlf)
)