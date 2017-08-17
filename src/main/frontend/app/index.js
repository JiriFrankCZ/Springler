import React from 'react';
import ReactDOM from 'react-dom';
import {Menu} from './component/menu';
import {Activities} from "./component/activities";

const element =
	<div>
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
		<div className="container-fluid" id="root">
			<div className="row row-offcanvas row-offcanvas-left">
				<div className="col-xs-6 col-sm-3 sidebar-offcanvas" role="navigation">
					<Menu/>
				</div>
				<div className="col-xs-12 col-sm-9 content">
					<Activities size="10"/>
				</div>
			</div>
		</div>
	</div>;


ReactDOM.render(element, document.getElementById('root'));