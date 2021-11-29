function isNumberKey(evento) {
    return Number.isInteger(parseInt(evento.key, 10)) 
	|| evento.key === 'Delete'
	|| evento.key === 'Backspace'
	|| evento.key === 'ArrowLeft'
	|| evento.key === 'ArrowRight'
}