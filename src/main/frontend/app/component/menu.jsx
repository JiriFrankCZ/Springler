import React from 'react';
import {Link} from "react-router";

export class Menu extends React.Component {
	render() {
		return <ul className="list-group panel">
			<li className="list-group-item {this.props.active === 'dashboard' ? 'active'}"><Link to="/"><i className="glyphicon glyphicon-dashboard "></i>Dashboard</Link></li>
			<li className="list-group-item {this.props.active === 'activities' ? 'active'}"><Link to="/activities"><i className="glyphicon glyphicon-align-justify "></i>Activites</Link></li>
			<li className="list-group-item {this.props.active === 'configuration' ? 'active'}"><Link to="/configuration"><i className="glyphicon glyphicon-cog "></i>Configuration</Link></li>
		</ul>;
	}
}