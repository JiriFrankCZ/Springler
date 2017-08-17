import React from 'react';

export class Menu extends React.Component {
	render() {
		return <ul className="list-group panel">
			<li className="list-group-item"><a href="forms.html"><i className="glyphicon glyphicon-dashboard"></i>Dashboard</a></li>
			<li className="list-group-item"><a href="forms.html"><i className="glyphicon glyphicon-align-justify"></i>Activites</a></li>
			<li className="list-group-item"><a href="alerts.html"><i className="glyphicon glyphicon-cog"></i>Configuration</a></li>
		</ul>;
	}
}