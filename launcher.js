/**
 * @format
 */
import './App/Config/ReactotronConfig';
import {AppRegistry} from 'react-native';
import ReduxContainer from './App/Containers/ReduxContainer';
import {name as appName} from './app.json';
import App from "./App"

AppRegistry.registerComponent(appName, () => ReduxContainer);
