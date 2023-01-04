(deffacts Hechos
	(nombre Juanjo) 
	(nombre Juan) 
	(apellido-1 Cruz) 
	(apellido-1 Perez)
	(apellido-2 Lopez)
	(nombre Federico) 
	(apellido-1 Perez) 
	(apellido-2 Jimenez)
	(nombre Alex)
	(apellido-1 Baquero)
	(apellido-2 Dominguez)
)

(defrule Regla_1
	(nombre Alex)
	(apellido-1 Baquero)
	(apellido-2 Dominguez) 
	=>
	(printout t crlf "Hola Alex Baquero Dominguez");
)

(defrule Regla_2
	(nombre Alex)
	=>
	(printout t crlf "Hola Alex");
)





