import React from 'react';
import ReactDOM from 'react-dom';
import {Menu} from './component/menu';
import {Activities} from "./component/activities";
import {HumidityChart} from "./component/humidity-chart";
import {IndexRoute, Route, Router} from "react-router";
import {Configuration} from "./component/configuration";


const navbar =
	<nav role="navigation" className="navbar navbar-custom">
		<div className="container-fluid">
			<div className="navbar-header">
				<button data-target="#bs-content-row-navbar-collapse-5" data-toggle="collapse"
				        className="navbar-toggle" type="button">
					<span className="sr-only">Toggle navigation</span>
					<span className="icon-bar"></span>
					<span className="icon-bar"></span>
					<span className="icon-bar"></span>
				</button>
				<a href="#" className="navbar-brand">Springler</a>
			</div>
		</div>
	</nav>
;

class DashboardPage extends React.Component {
	render() {
		return <div>
			{navbar}
			<div className="container-fluid" id="root">
				<div className="row row-offcanvas row-offcanvas-left">
					<div className="col-xs-6 col-sm-3 sidebar-offcanvas" role="navigation">
						<Menu active="dashboard"/>
					</div>
					<div className="col-xs-12 col-sm-9 content">
						<HumidityChart/>
						<Activities size="10"/>
					</div>
				</div>
			</div>
		</div>;
	}
}

class ActivitiesPage extends React.Component {
	render() {
		return <div>
			{navbar}
			<div className="container-fluid" id="root">
				<div className="row row-offcanvas row-offcanvas-left">
					<div className="col-xs-6 col-sm-3 sidebar-offcanvas" role="navigation">
						<Menu active="activities"/>
					</div>
					<div className="col-xs-12 col-sm-9 content">
						<Activities size="100"/>
					</div>
				</div>
			</div>
		</div>;
	}
}

class ConfigurationPage extends React.Component {
	render() {
		return <div>
			{navbar}
			<div className="container-fluid" id="root">
				<div className="row row-offcanvas row-offcanvas-left">
					<div className="col-xs-6 col-sm-3 sidebar-offcanvas" role="navigation">
						<Menu active="configuration"/>
					</div>
					<div className="col-xs-12 col-sm-9 content">
						<Configuration/>
					</div>
				</div>
			</div>
		</div>;
	}
}


ReactDOM.render(
	<Router>
		<Route path='/' component={DashboardPage}/>
		<Route path='/activities' component={ActivitiesPage}/>
		<Route path='/configuration' component={ConfigurationPage}/>
	</Router>
, document.getElementById('root'));