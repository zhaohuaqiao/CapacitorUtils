import { WebPlugin } from '@capacitor/core';
import { CapacitorUtilsPlugin } from './definitions';
export declare class CapacitorUtilsWeb extends WebPlugin implements CapacitorUtilsPlugin {
    constructor();
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
declare const CapacitorUtils: CapacitorUtilsWeb;
export { CapacitorUtils };
